package io.freshdroid.vinyl.core.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class HttpTransition(
        val verb: HttpVerb,
        val url: String,
        val accept: String? = null
) : Parcelable