package io.freshdroid.vinyl.factories

import io.freshdroid.vinyl.commons.models.Album

object AlbumFactory {

    @JvmStatic
    fun create(): Album {
        return Album(
            "id",
            "title",
            "link",
            PictureFactory.create(),
            10,
            "1990-02",
            "recordType",
            true,
            "tracklist",
            false,
            1552595610,
            ArtistFactory.create(),
            "type"
        )
    }

}