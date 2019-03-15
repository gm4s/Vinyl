package io.freshdroid.vinyl.features.home.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.freshdroid.vinyl.commons.ApplicationMap
import io.freshdroid.vinyl.commons.ButtonConfig
import io.freshdroid.vinyl.commons.models.Album
import io.freshdroid.vinyl.core.lib.ActivityViewModel
import io.freshdroid.vinyl.core.network.envelopes.ErrorEnvelope
import io.freshdroid.vinyl.core.rx.Irrelevant
import io.freshdroid.vinyl.core.rx.transformers.Transformers.pipeApiErrorsTo
import io.freshdroid.vinyl.core.rx.transformers.Transformers.pipeErrorsTo
import io.freshdroid.vinyl.core.utils.ExceptionPredicate
import io.freshdroid.vinyl.features.home.HomeEnvironment
import io.freshdroid.vinyl.features.home.di.HomeComponentManager
import io.freshdroid.vinyl.features.home.viewmodels.errors.HomeViewModelErrors
import io.freshdroid.vinyl.features.home.viewmodels.inputs.HomeViewModelInputs
import io.freshdroid.vinyl.features.home.viewmodels.outputs.HomeViewModelOutputs
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.concurrent.TimeUnit

class HomeViewModel(
    environment: HomeEnvironment,
    scopeProvider: AndroidLifecycleScopeProvider
) : ActivityViewModel(), HomeViewModelInputs, HomeViewModelOutputs, HomeViewModelErrors {

    private val mFetchAlbums = PublishSubject.create<Irrelevant>()
    private val mFetchNextAlbums = PublishSubject.create<Irrelevant>()
    private val mAlbums = PublishSubject.create<ArrayList<Album>>()
    private val mFetchAlbumsApiError = PublishSubject.create<ErrorEnvelope>()
    private val mFetchAlbumsError = PublishSubject.create<Throwable>()
    private val mFetchAlbumsNetworkError = PublishSubject.create<Irrelevant>()
    private val mAlbumClicked = PublishSubject.create<Pair<Uri, Album>>()
    private val mDisplayAlbumDetails = PublishSubject.create<Pair<Uri, Album>>()

    private val mApiHome = environment.apiHome
    private val mScheduler = environment.scheduler

    val inputs: HomeViewModelInputs = this
    val outputs: HomeViewModelOutputs = this
    val errors: HomeViewModelErrors = this

    init {
        mFetchAlbums
            .switchMap { this.fetchUserAlbums() }
            .autoDisposable(scopeProvider)
            .subscribe(mAlbums::onNext)

        mFetchNextAlbums
            .autoDisposable(scopeProvider)
            .subscribe { Timber.d("fetch next") }

        mFetchAlbumsError
            .filter(ExceptionPredicate::isNetworkException)
            .autoDisposable(scopeProvider)
            .subscribe { mFetchAlbumsNetworkError.onNext(Irrelevant.INSTANCE) }

        mAlbumClicked
            .throttleFirst(ButtonConfig.MULTI_CLICK_PROTECT_DURATION, TimeUnit.MILLISECONDS, mScheduler)
            .autoDisposable(scopeProvider)
            .subscribe(mDisplayAlbumDetails::onNext)
    }

    override fun onCleared() {
        super.onCleared()
        HomeComponentManager.destroyHomeComponent()
    }

    // INPUTS

    override fun fetchAlbums() {
        mFetchAlbums.onNext(Irrelevant.INSTANCE)
    }

    override fun fetchNextAlbums() {
        mFetchNextAlbums.onNext(Irrelevant.INSTANCE)
    }

    override fun onAlbumClick(album: Album) {
        mAlbumClicked.onNext(Pair(Uri.parse(ApplicationMap.ALBUM_DETAILS), album))
    }

    // OUTPUTS

    override fun albums(): Observable<ArrayList<Album>> = mAlbums

    override fun displayAlbumDetails(): Observable<Pair<Uri, Album>> = mDisplayAlbumDetails

    // ERRORS

    override fun fetchAlbumsApiError(): Observable<ErrorEnvelope> = mFetchAlbumsApiError

    override fun fetchAlbumsNetworkError(): Observable<Irrelevant> = mFetchAlbumsNetworkError

    private fun fetchUserAlbums(): Observable<ArrayList<Album>> {
        return mApiHome.fetchUserAlbums()
            .compose(pipeApiErrorsTo(mFetchAlbumsApiError))
            .compose(pipeErrorsTo(mFetchAlbumsError))
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val environment: HomeEnvironment,
        private val scopeProvider: AndroidLifecycleScopeProvider
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HomeViewModel(environment, scopeProvider) as T
        }
    }

}