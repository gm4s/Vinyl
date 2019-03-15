package io.freshdroid.vinyl.features.home.network.envelopes

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArtistEnvelope(
    @Json(name = "id") val id: String? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "picture") val picture: String? = null,
    @Json(name = "picture_small") val pictureSmall: String? = null,
    @Json(name = "picture_medium") val pictureMedium: String? = null,
    @Json(name = "pictureBig") val pictureBig: String? = null,
    @Json(name = "pictureXl") val pictureXl: String? = null,
    @Json(name = "tracklist") val tracklist: String? = null,
    @Json(name = "type") val type: String? = null

)