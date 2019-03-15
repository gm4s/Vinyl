package io.freshdroid.vinyl.features.home.network

import com.squareup.moshi.Moshi
import io.freshdroid.vinyl.commons.models.Album
import io.freshdroid.vinyl.core.network.ApiCore
import io.freshdroid.vinyl.core.network.HttpTransition
import io.freshdroid.vinyl.core.network.HttpTransitionFactoryType
import io.freshdroid.vinyl.core.network.HttpVerb
import io.freshdroid.vinyl.features.home.network.adapters.AlbumListAdapter
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class ApiHome(
    private val httpTransitionFactory: HttpTransitionFactoryType,
    private val moshi: Moshi
) : ApiCore(moshi), ApiHomeType {

    override fun fetchUserAlbums(): Observable<ArrayList<Album>> {
        val httpTransition = HttpTransition(
            verb = HttpVerb.GET,
            url = "http://api.deezer.com/2.0/user/2529/albums"
        )

        return httpTransitionFactory.create(transition = httpTransition)
            .lift(apiErrorOperator())
            .map { AlbumListAdapter.fromJson(moshi, it) }
            .subscribeOn(Schedulers.io())
    }

}