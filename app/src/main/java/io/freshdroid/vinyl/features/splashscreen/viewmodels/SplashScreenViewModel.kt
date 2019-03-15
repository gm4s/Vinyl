package io.freshdroid.vinyl.features.splashscreen.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.freshdroid.vinyl.commons.ApplicationMap
import io.freshdroid.vinyl.core.lib.ActivityViewModel
import io.freshdroid.vinyl.core.rx.Irrelevant
import io.freshdroid.vinyl.features.splashscreen.SplashScreenEnvironment
import io.freshdroid.vinyl.features.splashscreen.di.SplashScreenComponentManager
import io.freshdroid.vinyl.features.splashscreen.viewmodels.errors.SplashScreenViewModelErrors
import io.freshdroid.vinyl.features.splashscreen.viewmodels.inputs.SplashScreenViewModelInputs
import io.freshdroid.vinyl.features.splashscreen.viewmodels.outputs.SplashScreenViewModelOutputs
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SplashScreenViewModel(
    environment: SplashScreenEnvironment,
    scopeProvider: AndroidLifecycleScopeProvider
) : ActivityViewModel(), SplashScreenViewModelInputs, SplashScreenViewModelOutputs, SplashScreenViewModelErrors {

    private val mFakeFetchData = PublishSubject.create<Irrelevant>()
    private val mNextNavigationState = PublishSubject.create<Uri>()

    private val mScheduler = environment.scheduler

    val inputs: SplashScreenViewModelInputs = this
    val outputs: SplashScreenViewModelOutputs = this
    val errors: SplashScreenViewModelErrors = this

    init {
        mFakeFetchData
            .delay(1500, TimeUnit.MILLISECONDS, mScheduler)
            .autoDisposable(scopeProvider)
            .subscribe { mNextNavigationState.onNext(Uri.parse(ApplicationMap.HOME)) }
    }

    override fun onCleared() {
        super.onCleared()
        SplashScreenComponentManager.destroySplashScreenComponent()
    }

    // INPUTS

    override fun fakeFetchData() {
        mFakeFetchData.onNext(Irrelevant.INSTANCE)
    }

    // OUTPUTS

    override fun nextNavigationState(): Observable<Uri> = mNextNavigationState

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val environment: SplashScreenEnvironment,
        private val scopeProvider: AndroidLifecycleScopeProvider
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SplashScreenViewModel(environment, scopeProvider) as T
        }
    }

}