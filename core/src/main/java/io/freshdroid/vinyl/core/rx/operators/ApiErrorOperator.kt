package io.freshdroid.vinyl.core.rx.operators

import com.squareup.moshi.Moshi
import io.freshdroid.vinyl.core.network.envelopes.ErrorEnvelope
import io.freshdroid.vinyl.core.network.exceptions.ApiException
import io.freshdroid.vinyl.core.network.exceptions.ResponseException
import io.reactivex.ObservableOperator
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.Response


class ApiErrorOperator<T>(
        private val moshi: Moshi
) : ObservableOperator<T, Response<T>> {

    override fun apply(observer: Observer<in T>): Observer<in Response<T>> {
        return object : Observer<Response<T>> {

            override fun onComplete() {
                observer.onComplete()
            }

            override fun onSubscribe(d: Disposable) {
                observer.onSubscribe(d)
            }

            override fun onNext(response: Response<T>) {
                if (!response.isSuccessful) {
                    try {
                        val adapter = moshi.adapter(ErrorEnvelope::class.java)
                        val envelope = adapter.fromJson(response.errorBody()?.string() ?: "{}")

                        if (envelope != null) {
                            val envelopeWithCodeError = envelope.copy(code = response.code())
                            observer.onError(ApiException(envelopeWithCodeError, response))
                        } else {
                            observer.onError(ResponseException(response))
                        }
                    } catch (e: Exception) {
                        observer.onError(ResponseException(response))
                    }
                } else {
                    observer.onNext(response.body()!!)
                    observer.onComplete()
                }
            }

            override fun onError(e: Throwable) {
                observer.onError(e)
            }
        }
    }

}