package io.freshdroid.vinyl.features.splashscreen.viewmodels.outputs

import android.net.Uri
import io.reactivex.Observable

interface SplashScreenViewModelOutputs {

    fun nextNavigationState(): Observable<Uri>

}