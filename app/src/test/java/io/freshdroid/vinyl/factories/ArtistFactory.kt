package io.freshdroid.vinyl.factories

import io.freshdroid.vinyl.commons.models.Artist

object ArtistFactory {

    @JvmStatic
    fun create(): Artist {
        return Artist(
            "id",
            "name",
            PictureFactory.create(),
            "tracklist",
            "type"
        )
    }

}