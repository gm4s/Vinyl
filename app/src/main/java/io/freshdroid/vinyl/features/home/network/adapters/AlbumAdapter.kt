package io.freshdroid.vinyl.features.home.network.adapters

import io.freshdroid.vinyl.commons.models.Album
import io.freshdroid.vinyl.commons.models.Picture
import io.freshdroid.vinyl.features.home.network.envelopes.AlbumEnvelope

object AlbumAdapter {

    @JvmStatic
    fun fromEnvelope(albumEnvelope: AlbumEnvelope): Album {
        return Album(
            albumEnvelope.id,
            albumEnvelope.title,
            albumEnvelope.link,
            Picture(
                albumEnvelope.cover,
                albumEnvelope.coverSmall,
                albumEnvelope.coverMedium,
                albumEnvelope.coverBig,
                albumEnvelope.coverXl
            ),
            albumEnvelope.nbTracks,
            albumEnvelope.releaseDate,
            albumEnvelope.recordType,
            albumEnvelope.available,
            albumEnvelope.tracklist,
            albumEnvelope.explicitLyrics,
            albumEnvelope.timeAdd,
            albumEnvelope.artist?.let { ArtistAdapter.fromEnvelope(albumEnvelope.artist) },
            albumEnvelope.type
        )
    }

}