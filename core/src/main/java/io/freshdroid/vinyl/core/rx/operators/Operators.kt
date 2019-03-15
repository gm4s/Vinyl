package io.freshdroid.vinyl.core.rx.operators

import com.squareup.moshi.Moshi


object Operators {

    @JvmStatic
    fun <T> apiError(moshi: Moshi): ApiErrorOperator<T> = ApiErrorOperator(moshi)

}