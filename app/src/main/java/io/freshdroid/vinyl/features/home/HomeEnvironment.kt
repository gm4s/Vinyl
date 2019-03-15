package io.freshdroid.vinyl.features.home

import io.freshdroid.vinyl.features.home.network.ApiHomeType
import io.reactivex.Scheduler

data class HomeEnvironment(
    val apiHome: ApiHomeType,
    val scheduler: Scheduler
)