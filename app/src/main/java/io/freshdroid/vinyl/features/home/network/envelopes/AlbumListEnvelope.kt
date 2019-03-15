package io.freshdroid.vinyl.features.home.network.envelopes

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.freshdroid.vinyl.features.home.network.envelopes.AlbumEnvelope

@JsonClass(generateAdapter = true)
data class AlbumListEnvelope(
    @Json(name = "data") val data: List<AlbumEnvelope>? = null
)