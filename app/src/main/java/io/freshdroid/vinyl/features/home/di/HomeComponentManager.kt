package io.freshdroid.vinyl.features.home.di

import io.freshdroid.vinyl.core.CoreComponent
import javax.inject.Singleton


@Singleton
object HomeComponentManager {

    private var homeComponent: HomeComponent? = null

    fun homeComponent(coreComponent: CoreComponent): HomeComponent {
        if (homeComponent == null)
            homeComponent = DaggerHomeComponent.builder().coreComponent(coreComponent).build()
        return homeComponent as HomeComponent
    }

    fun destroyHomeComponent() {
        homeComponent = null
    }

}