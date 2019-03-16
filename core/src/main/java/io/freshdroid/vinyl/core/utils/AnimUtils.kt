package io.freshdroid.vinyl.core.utils

import android.content.Context
import android.os.Build
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import androidx.annotation.RequiresApi


object AnimUtils {

    private var fastOutSlowIn: Interpolator? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun getFastOutSlowInInterpolator(context: Context): Interpolator {
        if (fastOutSlowIn == null) {
            fastOutSlowIn = AnimationUtils.loadInterpolator(context, android.R.interpolator.fast_out_slow_in)
        }
        return requireNotNull(fastOutSlowIn)
    }

}