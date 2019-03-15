package io.freshdroid.vinyl.core.exceptions


class IntentExtraNotFoundException(key: String) : RuntimeException("Unable to find the extras associated to the intent with the key: $key")