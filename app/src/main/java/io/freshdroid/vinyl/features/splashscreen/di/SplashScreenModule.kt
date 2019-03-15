package io.freshdroid.vinyl.features.splashscreen.di

import dagger.Module
import dagger.Provides
import io.freshdroid.vinyl.features.splashscreen.SplashScreenEnvironment
import io.reactivex.Scheduler

@Module
class SplashScreenModule {

    @Provides
    @SplashScreenScope
    fun provideSplashScreenEnvironment(
        scheduler: Scheduler
    ): SplashScreenEnvironment {
        return SplashScreenEnvironment(scheduler)
    }

}