package io.freshdroid.vinyl.features.home.network.envelopes

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AlbumEnvelope(
    @Json(name = "id") val id: String? = null,
    @Json(name = "title") val title: String? = null,
    @Json(name = "link") val link: String? = null,
    @Json(name = "cover") val cover: String? = null,
    @Json(name = "cover_small") val coverSmall: String? = null,
    @Json(name = "cover_medium") val coverMedium: String? = null,
    @Json(name = "cover_big") val coverBig: String? = null,
    @Json(name = "cover_xl") val coverXl: String? = null,
    @Json(name = "nb_tracks") val nbTracks: Int? = null,
    @Json(name = "release_date") val releaseDate: String? = null,
    @Json(name = "record_type") val recordType: String? = null,
    @Json(name = "available") val available: Boolean? = null,
    @Json(name = "tracklist") val tracklist: String? = null,
    @Json(name = "explicit_lyrics") val explicitLyrics: Boolean? = null,
    @Json(name = "time_add") val timeAdd: Long? = null,
    @Json(name = "artist") val artist: ArtistEnvelope? = ArtistEnvelope(),
    @Json(name = "type") val type: String? = null
)