package io.freshdroid.vinyl.core.utils

import io.freshdroid.vinyl.core.network.envelopes.ErrorEnvelope


object ExceptionPredicate {

    @JvmStatic
    fun isNetworkException(t: Throwable): Boolean {
        return t.message?.contains("Unable to resolve host", ignoreCase = true) ?: false
    }

    @JvmStatic
    fun isBadRequestException(envelope: ErrorEnvelope): Boolean {
        return envelope.code == 400
    }

    @JvmStatic
    fun isResourceNotFoundException(envelope: ErrorEnvelope): Boolean {
        return envelope.code == 404
    }

    @JvmStatic
    fun isForbiddenException(envelope: ErrorEnvelope): Boolean {
        return envelope.code == 403
    }

    @JvmStatic
    fun isClientErrorException(envelope: ErrorEnvelope): Boolean {
        return envelope.code in 400 until 500
    }

}