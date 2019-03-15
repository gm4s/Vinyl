package io.freshdroid.vinyl.features.album.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.freshdroid.vinyl.commons.models.Album
import io.freshdroid.vinyl.core.lib.ActivityViewModel
import io.freshdroid.vinyl.core.lib.IntentExtra.getParcelableFromKey
import io.freshdroid.vinyl.core.rx.Irrelevant
import io.freshdroid.vinyl.features.album.viewmodels.errors.AlbumDetailsViewModelErrors
import io.freshdroid.vinyl.features.album.viewmodels.inputs.AlbumDetailsViewModelInputs
import io.freshdroid.vinyl.features.album.viewmodels.outputs.AlbumDetailsViewModelOutputs
import io.freshdroid.vinyl.features.album.views.AlbumDetailsActivity
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class AlbumDetailsViewModel(
    scopeProvider: AndroidLifecycleScopeProvider
) : ActivityViewModel(), AlbumDetailsViewModelInputs, AlbumDetailsViewModelOutputs, AlbumDetailsViewModelErrors {

    private val mBackClicked = PublishSubject.create<Irrelevant>()
    private val mAlbum = PublishSubject.create<Album>()

    private val mBack: Observable<Irrelevant>

    val inputs: AlbumDetailsViewModelInputs = this
    val outputs: AlbumDetailsViewModelOutputs = this
    val errors: AlbumDetailsViewModelErrors = this

    init {
        getParcelableFromKey<Album>(intent(), AlbumDetailsActivity.KEY_ALBUM, true)
            .autoDisposable(scopeProvider)
            .subscribe(mAlbum::onNext)

        mBack = mBackClicked
    }

    // INPUTS

    override fun onBackClick() {
        mBackClicked.onNext(Irrelevant.INSTANCE)
    }

    // OUTPUTS

    override fun back(): Observable<Irrelevant> = mBack

    override fun album(): Observable<Album> = mAlbum

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val scopeProvider: AndroidLifecycleScopeProvider
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AlbumDetailsViewModel(scopeProvider) as T
        }
    }

}