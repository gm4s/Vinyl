package io.freshdroid.vinyl.features.home

import io.freshdroid.vinyl.commons.models.Album
import io.freshdroid.vinyl.features.home.network.ApiHomeType
import io.reactivex.Observable

open class MockApiHome : ApiHomeType {

    override fun fetchUserAlbums(): Observable<ArrayList<Album>> = Observable.empty()

}