package io.freshdroid.vinyl.features.home.network

import io.freshdroid.vinyl.commons.models.Album
import io.reactivex.Observable

interface ApiHomeType {

    fun fetchUserAlbums(): Observable<ArrayList<Album>>

}