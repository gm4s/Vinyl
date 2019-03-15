package io.freshdroid.vinyl.features.splashscreen.views

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.uber.autodispose.autoDisposable
import io.freshdroid.vinyl.R
import io.freshdroid.vinyl.commons.IntentBuilder
import io.freshdroid.vinyl.core.extensions.launchIntent
import io.freshdroid.vinyl.core.rx.transformers.Transformers.observeForUI
import io.freshdroid.vinyl.core.ui.BaseActivity
import io.freshdroid.vinyl.core.ui.TransitionUtils.fadeIn
import io.freshdroid.vinyl.coreComponent
import io.freshdroid.vinyl.features.splashscreen.di.SplashScreenComponentManager
import io.freshdroid.vinyl.features.splashscreen.viewmodels.SplashScreenViewModel

class SplashScreenActivity : BaseActivity() {

    private val component by lazy { SplashScreenComponentManager.splashScreenComponent(coreComponent()) }
    private val viewModelFactory by lazy { SplashScreenViewModel.Factory(component.environment(), scopeProvider) }
    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(SplashScreenViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        viewModel.outputs.nextNavigationState()
            .compose(observeForUI())
            .map { IntentBuilder.build(this, it, true) }
            .autoDisposable(scopeProvider)
            .subscribe { this.launchIntent(it, fadeIn()) }

        viewModel.intent(intent)
        viewModel.inputs.fakeFetchData()
    }

}