package io.freshdroid.vinyl.core.utils

object MathUtils {

    fun constrain(min: Float, max: Float, v: Float) = v.coerceIn(min, max)

}