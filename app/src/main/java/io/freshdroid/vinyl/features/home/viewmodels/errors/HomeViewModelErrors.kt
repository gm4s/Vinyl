package io.freshdroid.vinyl.features.home.viewmodels.errors

import io.freshdroid.vinyl.core.network.envelopes.ErrorEnvelope
import io.freshdroid.vinyl.core.rx.Irrelevant
import io.reactivex.Observable

interface HomeViewModelErrors {

    fun fetchAlbumsApiError(): Observable<ErrorEnvelope>

    fun fetchAlbumsNetworkError(): Observable<Irrelevant>

}