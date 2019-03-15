package io.freshdroid.vinyl.commons.exceptions

import android.net.Uri


class IntentNotFoundException(uri: Uri) : RuntimeException("Unable to create the associated intent to the Uri: $uri")