package io.freshdroid.vinyl.commons

import android.content.Context
import android.content.Intent
import android.net.Uri
import io.freshdroid.vinyl.features.album.views.AlbumDetailsActivity
import io.freshdroid.vinyl.features.home.views.HomeActivity
import io.freshdroid.vinyl.commons.exceptions.IntentNotFoundException


object UriResolver {

    @JvmStatic
    @Throws(IntentNotFoundException::class)
    fun resolve(context: Context, uri: Uri, popBackStack: Boolean): Intent {
        return getIntent(context, uri, popBackStack)
    }

    private fun getIntent(context: Context, uri: Uri, popBackStack: Boolean): Intent {
        return when (uri.toString()) {
            ApplicationMap.HOME -> getIntentHomeActivity(context, popBackStack)
            ApplicationMap.ALBUM_DETAILS -> getIntentAlbumDetailsActivity(context, popBackStack)
            else -> throw IntentNotFoundException(uri)
        }
    }

    private fun getIntentHomeActivity(context: Context, popBackStack: Boolean): Intent {
        val intent = Intent(context, HomeActivity::class.java)

        if (popBackStack) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        return intent
    }

    private fun getIntentAlbumDetailsActivity(context: Context, popBackStack: Boolean): Intent {
        val intent = Intent(context, AlbumDetailsActivity::class.java)

        if (popBackStack) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        return intent
    }

}