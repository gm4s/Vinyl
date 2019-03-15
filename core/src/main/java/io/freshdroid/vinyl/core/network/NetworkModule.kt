package io.freshdroid.vinyl.core.network

import android.content.Context
import com.moczul.ok2curl.CurlInterceptor
import com.moczul.ok2curl.logger.Loggable
import com.readystatesoftware.chuck.ChuckInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.freshdroid.vinyl.core.BuildConfig
import io.freshdroid.vinyl.core.lib.Build
import io.freshdroid.vinyl.core.lib.CurrentUserType
import io.freshdroid.vinyl.core.network.interceptors.ApiRequestInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import timber.log.Timber
import java.util.*
import javax.inject.Singleton


@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .build()
    }

    @Provides
    @Singleton
    internal fun provideApiRequestInterceptor(
        currentUser: CurrentUserType,
        build: Build,
        locale: Locale
    ): ApiRequestInterceptor {
        return ApiRequestInterceptor(build, locale, currentUser)
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(
        context: Context,
        apiRequestInterceptor: ApiRequestInterceptor,
        loggingInterceptor: HttpLoggingInterceptor,
        build: Build
    ): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.addInterceptor(apiRequestInterceptor)

        if (build.isDebug()) {
            client.addInterceptor(loggingInterceptor)
            client.addInterceptor(ChuckInterceptor(context))
            client.addInterceptor(CurlInterceptor(Loggable { Timber.d("D/OkHttp: $it") }))
        }

        return client.build()
    }

    @Provides
    @Singleton
    fun provideApiRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return if (BuildConfig.DEBUG) {
            createRetrofit(ApiEndpoint.VINYL_DEV.url, okHttpClient)
        } else {
            createRetrofit(ApiEndpoint.VINYL.url, okHttpClient)
        }
    }

    private fun createRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(StringConverterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideHttpTransitionFactoryType(apiService: ApiService): HttpTransitionFactoryType {
        return HttpTransitionFactory(apiService)
    }

}