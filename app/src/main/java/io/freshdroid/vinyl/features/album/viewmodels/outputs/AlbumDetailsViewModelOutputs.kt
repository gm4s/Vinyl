package io.freshdroid.vinyl.features.album.viewmodels.outputs

import io.freshdroid.vinyl.commons.models.Album
import io.freshdroid.vinyl.core.rx.Irrelevant
import io.reactivex.Observable

interface AlbumDetailsViewModelOutputs {

    fun back(): Observable<Irrelevant>

    fun album(): Observable<Album>

}