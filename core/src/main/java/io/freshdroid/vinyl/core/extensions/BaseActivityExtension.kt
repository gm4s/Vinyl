@file:JvmName("BaseActivityExtension")

package io.freshdroid.vinyl.core.extensions

import android.content.Intent
import androidx.annotation.StringRes
import io.freshdroid.vinyl.core.network.envelopes.ErrorEnvelope
import io.freshdroid.vinyl.core.ui.BaseActivity
import io.freshdroid.vinyl.core.ui.TransitionUtils.slideInRight
import io.freshdroid.vinyl.core.ui.TransitionUtils.transition

fun BaseActivity.showToastApiError(errorEnvelope: ErrorEnvelope) {
    errorEnvelope.error?.let { android.widget.Toast.makeText(this, it, android.widget.Toast.LENGTH_LONG).show() }
}

fun BaseActivity.showToastError(@StringRes messageResource: Int) {
    android.widget.Toast.makeText(this, getString(messageResource), android.widget.Toast.LENGTH_LONG).show()
}

fun BaseActivity.showToastMessage(@StringRes messageResource: Int) {
    android.widget.Toast.makeText(this, getString(messageResource), android.widget.Toast.LENGTH_LONG).show()
}

fun BaseActivity.showToastMessage(message: String) {
    android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_LONG).show()
}

fun BaseActivity.launchIntent(intent: Intent, transition: Pair<Int, Int> = slideInRight()) {
    startActivity(intent)
    transition(this, transition)
}