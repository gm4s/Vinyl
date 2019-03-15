package io.freshdroid.vinyl.core.network.exceptions

import io.freshdroid.vinyl.core.network.envelopes.ErrorEnvelope
import retrofit2.Response


open class ApiException(
    private val errorEnvelope: ErrorEnvelope,
    response: Response<*>
) : ResponseException(response) {

    fun errorEnvelope(): ErrorEnvelope = errorEnvelope

}