@file:JvmName("BaseFragmentExtension")

package io.freshdroid.vinyl.core.extensions

import android.widget.Toast
import androidx.annotation.StringRes
import io.freshdroid.vinyl.core.network.envelopes.ErrorEnvelope
import io.freshdroid.vinyl.core.ui.BaseFragment


fun BaseFragment.showToastApiError(errorEnvelope: ErrorEnvelope) {
    activity?.let { errorEnvelope.error?.let { Toast.makeText(activity, it, Toast.LENGTH_LONG).show() } }
}

fun BaseFragment.showToastError(@StringRes messageResource: Int) {
    activity?.let { Toast.makeText(activity, getString(messageResource), Toast.LENGTH_LONG).show() }
}

fun BaseFragment.showToastMessage(@StringRes messageResource: Int) {
    activity?.let { Toast.makeText(activity, getString(messageResource), Toast.LENGTH_LONG).show() }
}

fun BaseFragment.showToastMessage(message: String) {
    activity?.let { Toast.makeText(activity, message, Toast.LENGTH_LONG).show() }
}
