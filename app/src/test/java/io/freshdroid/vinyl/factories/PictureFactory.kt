package io.freshdroid.vinyl.factories

import io.freshdroid.vinyl.commons.models.Picture

object PictureFactory {

    @JvmStatic
    fun create(): Picture {
        return Picture(
            "thumb",
            "small",
            "medium",
            "large",
            "extraLarge"
        )
    }

}