package io.freshdroid.vinyl.features.home.views

import android.view.View
import io.freshdroid.vinyl.R
import io.freshdroid.vinyl.commons.models.Album
import io.freshdroid.vinyl.core.ui.BaseArrayAdapter
import io.freshdroid.vinyl.core.ui.BaseViewHolder

class AlbumAdapter(
    private val listener: Listener?
) : BaseArrayAdapter<Album>() {

    interface Listener : AlbumViewHolder.Listener

    override fun layout(item: Album): Int = R.layout.item_album

    override fun viewHolder(layout: Int, view: View): BaseViewHolder = AlbumViewHolder(view, listener)

    fun takeItems(items: ArrayList<Album>) {
        clearItems()
        addItems(items)
        notifyDataSetChanged()
    }

}