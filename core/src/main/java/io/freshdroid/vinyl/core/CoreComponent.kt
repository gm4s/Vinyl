package io.freshdroid.vinyl.core

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Component
import io.freshdroid.vinyl.core.lib.Build
import io.freshdroid.vinyl.core.lib.CurrentUserType
import io.freshdroid.vinyl.core.network.HttpTransitionFactoryType
import io.reactivex.Scheduler
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton


@Singleton
@Component(modules = [CoreModule::class])
interface CoreComponent {

    fun httpTransitionFactory(): HttpTransitionFactoryType
    fun retrofit(): Retrofit
    fun moshi(): Moshi
    fun scheduler(): Scheduler
    fun build(): Build
    fun okHttpClient(): OkHttpClient
    fun context(): Context
    fun currentUser(): CurrentUserType

}