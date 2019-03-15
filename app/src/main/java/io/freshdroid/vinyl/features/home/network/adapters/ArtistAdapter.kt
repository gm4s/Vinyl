package io.freshdroid.vinyl.features.home.network.adapters

import io.freshdroid.vinyl.commons.models.Artist
import io.freshdroid.vinyl.commons.models.Picture
import io.freshdroid.vinyl.features.home.network.envelopes.ArtistEnvelope

object ArtistAdapter {

    @JvmStatic
    fun fromEnvelope(artistEnvelope: ArtistEnvelope): Artist {
        return Artist(
            artistEnvelope.id,
            artistEnvelope.name,
            Picture(
                artistEnvelope.picture,
                artistEnvelope.pictureSmall,
                artistEnvelope.pictureMedium,
                artistEnvelope.pictureBig,
                artistEnvelope.pictureXl
            ),
            artistEnvelope.tracklist,
            artistEnvelope.type
        )
    }

}