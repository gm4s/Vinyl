package io.freshdroid.vinyl.features.splashscreen

import android.net.Uri
import io.freshdroid.vinyl.RobolectricTestCase
import io.freshdroid.vinyl.commons.ApplicationMap
import io.freshdroid.vinyl.features.splashscreen.di.DaggerSplashScreenComponent
import io.freshdroid.vinyl.features.splashscreen.di.SplashScreenComponent
import io.freshdroid.vinyl.features.splashscreen.viewmodels.SplashScreenViewModel
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

internal class SplashScreenViewModelTest : RobolectricTestCase() {

    private lateinit var component: SplashScreenComponent

    @Before
    fun initComponent() {
        component = DaggerSplashScreenComponent.builder().coreComponent(coreComponent()).build()
    }

    private fun environment(): SplashScreenEnvironment = component.environment()

    @Test
    fun testNextNavigationState() {
        val testScheduler = TestScheduler()
        val environment = environment().copy(
            scheduler = testScheduler
        )
        val vm = SplashScreenViewModel(environment, scopeProvider())
        val nextNavigationState = TestSubscriber<Uri>()
        vm.outputs.nextNavigationState().subscribe(nextNavigationState::onNext)

        vm.inputs.fakeFetchData()
        testScheduler.advanceTimeBy(500, TimeUnit.MILLISECONDS)
        nextNavigationState.assertNoValues()

        testScheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)
        nextNavigationState.assertValue { it == Uri.parse(ApplicationMap.HOME) }
    }

}