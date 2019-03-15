package io.freshdroid.vinyl.commons.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Album(
    val id: String?,
    val title: String?,
    val link: String?,
    val picture: Picture?,
    val nbTracks: Int?,
    val releaseDate: String?,
    val recordType: String?,
    val available: Boolean?,
    val tracklist: String?,
    val explicitLyrics: Boolean?,
    val timeAdd: Long?,
    val artist: Artist?,
    val type: String?
) : Parcelable
