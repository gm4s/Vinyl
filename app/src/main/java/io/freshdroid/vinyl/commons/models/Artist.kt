package io.freshdroid.vinyl.commons.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Artist(
    val id: String?,
    val name: String?,
    val picture: Picture?,
    val tracklist: String?,
    val type: String?
) : Parcelable