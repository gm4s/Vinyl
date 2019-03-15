package io.freshdroid.vinyl.core.lib

import io.freshdroid.vinyl.core.models.User
import io.reactivex.Observable


abstract class CurrentUserType {

    abstract fun refresh(freshUser: User)

    abstract fun toObservable(): Observable<User>

    abstract fun setAccessToken(accessToken: String)

    abstract fun getAccessToken(): String?

    abstract fun logout()

}