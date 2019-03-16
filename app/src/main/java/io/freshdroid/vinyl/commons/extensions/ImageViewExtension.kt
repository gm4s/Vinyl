@file:JvmName("ImageViewExtension")

package io.freshdroid.vinyl.commons.extensions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import io.freshdroid.vinyl.commons.models.Picture
import io.freshdroid.vinyl.core.glide.GlideApp


fun ImageView.loadPicture(@DrawableRes resource: Int) {
    GlideApp.with(this).load(resource).into(this)
}

fun ImageView.loadPicture(picture: Picture) {
    GlideApp.with(this)
        .load(picture.large)
        .thumbnail(getThumbnailRequest(this, picture))
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun ImageView.loadPicture(picture: Picture, listener: RequestListener<Drawable>) {
    GlideApp.with(this)
        .load(picture.large)
        .thumbnail(getThumbnailRequest(this, picture))
        .listener(listener)
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .priority(Priority.IMMEDIATE)
        .into(this)
}

private fun getThumbnailRequest(view: ImageView, picture: Picture) = GlideApp.with(view).load(picture.thumb)
