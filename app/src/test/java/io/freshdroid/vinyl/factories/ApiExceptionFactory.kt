package io.freshdroid.vinyl.factories

import io.freshdroid.vinyl.core.network.envelopes.ErrorEnvelope
import io.freshdroid.vinyl.core.network.exceptions.ApiException
import okhttp3.ResponseBody
import retrofit2.Response


object ApiExceptionFactory {

    fun badRequestException(): ApiException {
        val envelope = ErrorEnvelope(
                "bad request",
                400
        )
        val body = ResponseBody.create(null, "")
        val response = Response.error<ResponseBody>(400, body)

        return ApiException(envelope, response)
    }

    fun resourceNotFoundException(): ApiException {
        val envelope = ErrorEnvelope(
                "resource not found",
                404
        )
        val body = ResponseBody.create(null, "")
        val response = Response.error<ResponseBody>(404, body)

        return ApiException(envelope, response)
    }

}