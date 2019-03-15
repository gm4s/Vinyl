package io.freshdroid.vinyl.core.utils

import android.content.Intent


object ActivityExtraPredicate {

    @JvmStatic
    fun hasExtra(intent: Intent, key: String): Boolean {
        return intent.hasExtra(key)
    }

    @JvmStatic
    fun hasNotExtra(intent: Intent, key: String): Boolean {
        return !hasExtra(intent, key)
    }

}