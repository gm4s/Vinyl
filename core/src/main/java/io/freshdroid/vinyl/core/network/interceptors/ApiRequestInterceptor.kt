package io.freshdroid.vinyl.core.network.interceptors

import io.freshdroid.vinyl.core.lib.Build
import io.freshdroid.vinyl.core.lib.CurrentUserType
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.*
import javax.inject.Inject

internal const val ANDROID_DEVICE_TYPE_STRING = "androidphone"

internal class ApiRequestInterceptor @Inject constructor(
    private val build: Build,
    private val locale: Locale,
    private val currentUser: CurrentUserType
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(request(chain.request()))
    }

    private fun request(initialRequest: Request): Request {
        var requestBuilder = initialRequest.newBuilder()
            .header("User-Agent", ANDROID_DEVICE_TYPE_STRING)
            .header("Agent-Version", build.versionCode().toString())
            .header("Accept", "application/json")
            .header("Accept-Language", locale.language)

        requestBuilder = addHeaderIfNotNull(requestBuilder, currentUser.getAccessToken(), "Authorization")

        return requestBuilder.build()
    }

    private fun addHeaderIfNotNull(requestBuilder: Request.Builder, value: String?, name: String): Request.Builder {
        if (!value.isNullOrEmpty()) {
            requestBuilder.addHeader(name, requireNotNull(value))
        }
        return requestBuilder
    }

}