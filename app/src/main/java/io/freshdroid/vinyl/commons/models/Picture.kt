package io.freshdroid.vinyl.commons.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Picture(
    val thumb: String?,
    val small: String?,
    val medium: String?,
    val large: String?,
    val extraLarge: String?
) : Parcelable