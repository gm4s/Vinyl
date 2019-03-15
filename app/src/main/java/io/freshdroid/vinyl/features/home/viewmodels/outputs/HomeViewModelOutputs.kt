package io.freshdroid.vinyl.features.home.viewmodels.outputs

import android.net.Uri
import io.freshdroid.vinyl.commons.models.Album
import io.reactivex.Observable

interface HomeViewModelOutputs {

    fun albums(): Observable<ArrayList<Album>>

    fun displayAlbumDetails(): Observable<Pair<Uri, Album>>

}