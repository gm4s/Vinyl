package io.freshdroid.vinyl.features.home.network.adapters

import com.squareup.moshi.Moshi
import io.freshdroid.vinyl.commons.models.Album
import io.freshdroid.vinyl.features.home.network.envelopes.AlbumListEnvelope

object AlbumListAdapter {

    @JvmStatic
    fun fromJson(moshi: Moshi, json: String): ArrayList<Album> {
        val sessionResultEnvelope = moshi.adapter(AlbumListEnvelope::class.java).fromJson(json)

        val albums = ArrayList<Album>()

        sessionResultEnvelope?.data?.forEach {
            albums.add(AlbumAdapter.fromEnvelope(it))
        }

        return albums
    }

}