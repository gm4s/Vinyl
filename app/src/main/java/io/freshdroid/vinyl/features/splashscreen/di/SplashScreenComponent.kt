package io.freshdroid.vinyl.features.splashscreen.di

import dagger.Component
import io.freshdroid.vinyl.core.CoreComponent
import io.freshdroid.vinyl.features.splashscreen.SplashScreenEnvironment

@SplashScreenScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [SplashScreenModule::class]
)
interface SplashScreenComponent {

    fun environment(): SplashScreenEnvironment

}