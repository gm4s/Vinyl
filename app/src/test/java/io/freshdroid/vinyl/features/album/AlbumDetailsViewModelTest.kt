package io.freshdroid.vinyl.features.album

import android.content.Intent
import io.freshdroid.vinyl.RobolectricTestCase
import io.freshdroid.vinyl.commons.models.Album
import io.freshdroid.vinyl.core.rx.Irrelevant
import io.freshdroid.vinyl.factories.AlbumFactory
import io.freshdroid.vinyl.features.album.viewmodels.AlbumDetailsViewModel
import io.freshdroid.vinyl.features.album.views.AlbumDetailsActivity
import io.reactivex.subscribers.TestSubscriber
import org.junit.Test

internal class AlbumDetailsViewModelTest : RobolectricTestCase() {

    @Test
    fun testBack() {
        val vm = AlbumDetailsViewModel(scopeProvider())
        val back = TestSubscriber<Irrelevant>()
        vm.outputs.back().subscribe(back::onNext)

        vm.inputs.onBackClick()
        back.assertValueCount(1)
    }

    @Test
    fun testAlbum() {
        val vm = AlbumDetailsViewModel(scopeProvider())
        val album = TestSubscriber<Album>()
        vm.outputs.album().subscribe(album::onNext)

        val albumDetailsIntent = Intent()
        albumDetailsIntent.putExtra(AlbumDetailsActivity.KEY_ALBUM, AlbumFactory.create())
        vm.intent(albumDetailsIntent)

        album.assertValue { it.id == "id" }
    }

}