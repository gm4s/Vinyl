package io.freshdroid.vinyl.commons

import android.content.Context
import android.content.Intent
import android.net.Uri


object IntentBuilder {

    @JvmStatic
    fun build(context: Context, uri: Uri, popBackStack: Boolean = false): Intent {
        return UriResolver.resolve(context, uri, popBackStack)
    }

}