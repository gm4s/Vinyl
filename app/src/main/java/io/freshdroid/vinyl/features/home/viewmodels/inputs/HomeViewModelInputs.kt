package io.freshdroid.vinyl.features.home.viewmodels.inputs

import io.freshdroid.vinyl.features.home.views.AlbumAdapter

interface HomeViewModelInputs : AlbumAdapter.Listener {

    fun fetchAlbums()

    fun fetchNextAlbums()

}