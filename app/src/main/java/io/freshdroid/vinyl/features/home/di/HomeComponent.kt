package io.freshdroid.vinyl.features.home.di

import dagger.Component
import io.freshdroid.vinyl.core.CoreComponent
import io.freshdroid.vinyl.features.home.HomeEnvironment

@HomeScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [HomeModule::class]
)
interface HomeComponent {

    fun environment(): HomeEnvironment

}