package io.freshdroid.vinyl.features.home.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.freshdroid.vinyl.core.network.HttpTransitionFactoryType
import io.freshdroid.vinyl.features.home.HomeEnvironment
import io.freshdroid.vinyl.features.home.network.ApiHome
import io.freshdroid.vinyl.features.home.network.ApiHomeType
import io.reactivex.Scheduler

@Module
class HomeModule {

    @Provides
    @HomeScope
    fun provideHomeEnvironment(
        apiHome: ApiHomeType,
        scheduler: Scheduler
    ): HomeEnvironment {
        return HomeEnvironment(apiHome, scheduler)
    }

    @Provides
    @HomeScope
    fun provideApiHomeType(
        httpTransitionFactory: HttpTransitionFactoryType,
        moshi: Moshi
    ): ApiHomeType {
        return ApiHome(
            httpTransitionFactory,
            moshi
        )
    }

}