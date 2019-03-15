package io.freshdroid.vinyl.core.network

import io.freshdroid.vinyl.core.lib.Secrets


internal enum class ApiEndpoint(
    val tag: String,
    val url: String
) {

    VINYL("vinyl", Secrets.Vinyl.API_BASE_URL),
    VINYL_DEV("vinyl dev", Secrets.Vinyl.API_BASE_URL_DEV)

}