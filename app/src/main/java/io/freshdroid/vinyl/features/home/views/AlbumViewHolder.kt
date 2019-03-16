package io.freshdroid.vinyl.features.home.views

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import io.freshdroid.vinyl.R
import io.freshdroid.vinyl.commons.extensions.loadPicture
import io.freshdroid.vinyl.commons.models.Album
import io.freshdroid.vinyl.core.extensions.bind
import io.freshdroid.vinyl.core.ui.BaseViewHolder

class AlbumViewHolder(
    view: View,
    private val listener: Listener?
) : BaseViewHolder(view) {

    interface Listener {
        fun onAlbumClick(album: Album)
    }

    private val albumImageView: ImageView by bind(R.id.albumImageView)
    private val albumTitleTextView: TextView by bind(R.id.albumTitleTextView)
    private val artistTitleTextView: TextView by bind(R.id.artistTitleTextView)

    private lateinit var mAlbum: Album

    override fun bindData(data: Any) {
        mAlbum = requireNotNull(data) as Album
    }

    override fun onBind() {
        val layoutParams = albumImageView.layoutParams as LinearLayout.LayoutParams
        layoutParams.height = Math.round(albumImageView.resources.displayMetrics.widthPixels / 2.0).toInt()
        albumImageView.layoutParams = layoutParams

        mAlbum.picture?.let { albumImageView.loadPicture(it) }

        albumImageView.setOnClickListener { listener?.onAlbumClick(mAlbum) }

        albumTitleTextView.text = mAlbum.title
        artistTitleTextView.text = mAlbum.artist?.name
    }

}