package io.freshdroid.vinyl.core.network

import com.squareup.moshi.Moshi
import io.freshdroid.vinyl.core.rx.operators.ApiErrorOperator
import io.freshdroid.vinyl.core.rx.operators.Operators


open class ApiCore(
        private val moshi: Moshi
) {

    fun <T> apiErrorOperator(): ApiErrorOperator<T> {
        return Operators.apiError(moshi)
    }

}