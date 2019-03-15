package io.freshdroid.vinyl.features.home

import android.net.Uri
import io.freshdroid.vinyl.RobolectricTestCase
import io.freshdroid.vinyl.commons.ApplicationMap
import io.freshdroid.vinyl.commons.models.Album
import io.freshdroid.vinyl.core.network.envelopes.ErrorEnvelope
import io.freshdroid.vinyl.core.rx.Irrelevant
import io.freshdroid.vinyl.factories.AlbumFactory
import io.freshdroid.vinyl.factories.ApiExceptionFactory
import io.freshdroid.vinyl.features.home.di.DaggerHomeComponent
import io.freshdroid.vinyl.features.home.di.HomeComponent
import io.freshdroid.vinyl.features.home.viewmodels.HomeViewModel
import io.reactivex.Observable
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test

internal class HomeViewModelTest : RobolectricTestCase() {

    private lateinit var component: HomeComponent

    @Before
    fun initComponent() {
        component = DaggerHomeComponent.builder().coreComponent(coreComponent()).build()
    }

    private fun environment(): HomeEnvironment = component.environment()

    @Test
    fun testAlbums() {
        val environment = environment().copy(
            apiHome = object : MockApiHome() {
                override fun fetchUserAlbums(): Observable<ArrayList<Album>> {
                    return Observable.just(
                        arrayListOf(
                            AlbumFactory.create(),
                            AlbumFactory.create(),
                            AlbumFactory.create()
                        )
                    )
                }
            }
        )

        val vm = HomeViewModel(environment, scopeProvider())
        val albums = TestSubscriber<ArrayList<Album>>()
        vm.outputs.albums().subscribe(albums::onNext)

        vm.inputs.fetchAlbums()
        albums.assertValue { it.size == 3 }
    }

    @Test
    fun testFetchAlbumsApiError() {
        val environment = environment().copy(
            apiHome = object : MockApiHome() {
                override fun fetchUserAlbums(): Observable<ArrayList<Album>> {
                    return Observable.error(ApiExceptionFactory.badRequestException())
                }
            }
        )

        val vm = HomeViewModel(environment, scopeProvider())
        val fetchAlbumsApiError = TestSubscriber<ErrorEnvelope>()
        vm.errors.fetchAlbumsApiError().subscribe(fetchAlbumsApiError::onNext)

        vm.inputs.fetchAlbums()
        fetchAlbumsApiError.assertValue { it.code == 400 && it.error == "bad request" }
    }

    @Test
    fun testFetchAlbumsNetworkError() {
        val environment = environment().copy(
            apiHome = object : MockApiHome() {
                override fun fetchUserAlbums(): Observable<ArrayList<Album>> {
                    return Observable.error(Throwable("Unable to resolve host"))
                }
            }
        )

        val vm = HomeViewModel(environment, scopeProvider())
        val fetchAlbumsNetworkError = TestSubscriber<Irrelevant>()
        vm.errors.fetchAlbumsNetworkError().subscribe(fetchAlbumsNetworkError::onNext)

        vm.inputs.fetchAlbums()
        fetchAlbumsNetworkError.assertValueCount(1)
    }

    @Test
    fun testDisplayAlbumDetails() {
        val vm = HomeViewModel(environment(), scopeProvider())
        val displayAlbumDetails = TestSubscriber<Pair<Uri, Album>>()
        vm.outputs.displayAlbumDetails().subscribe(displayAlbumDetails::onNext)

        vm.inputs.onAlbumClick(AlbumFactory.create())
        displayAlbumDetails.assertValue { (uri, album) ->
            uri == Uri.parse(ApplicationMap.ALBUM_DETAILS)
                    && album.id == "id"
        }
    }

}