package io.freshdroid.vinyl.features.home.views

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uber.autodispose.autoDisposable
import io.freshdroid.vinyl.R
import io.freshdroid.vinyl.commons.IntentBuilder
import io.freshdroid.vinyl.core.extensions.bind
import io.freshdroid.vinyl.core.extensions.launchIntent
import io.freshdroid.vinyl.core.extensions.showToastApiError
import io.freshdroid.vinyl.core.extensions.showToastError
import io.freshdroid.vinyl.core.rx.transformers.Transformers.observeForUI
import io.freshdroid.vinyl.core.ui.BaseActivity
import io.freshdroid.vinyl.core.ui.RecyclerViewPaginator
import io.freshdroid.vinyl.coreComponent
import io.freshdroid.vinyl.features.album.views.AlbumDetailsActivity
import io.freshdroid.vinyl.features.home.di.HomeComponentManager
import io.freshdroid.vinyl.features.home.viewmodels.HomeViewModel
import io.reactivex.functions.Action

class HomeActivity : BaseActivity() {

    private val component by lazy { HomeComponentManager.homeComponent(coreComponent()) }
    private val viewModelFactory by lazy { HomeViewModel.Factory(component.environment(), scopeProvider) }
    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java) }

    private val homeRecyclerView: RecyclerView by bind(R.id.homeRecyclerView)

    private lateinit var mAlbumAdapter: AlbumAdapter

    private var mRecyclerViewPaginator: RecyclerViewPaginator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initViews()

        viewModel.outputs.albums()
            .compose(observeForUI())
            .autoDisposable(scopeProvider)
            .subscribe(mAlbumAdapter::takeItems)

        viewModel.outputs.displayAlbumDetails()
            .compose(observeForUI())
            .map { (uri, album) ->
                IntentBuilder.build(this, uri).apply {
                    putExtra(AlbumDetailsActivity.KEY_ALBUM, album)
                }
            }
            .autoDisposable(scopeProvider)
            .subscribe { this.launchIntent(it) }

        viewModel.errors.fetchAlbumsApiError()
            .compose(observeForUI())
            .autoDisposable(scopeProvider)
            .subscribe { this.showToastApiError(it) }

        viewModel.errors.fetchAlbumsNetworkError()
            .compose(observeForUI())
            .autoDisposable(scopeProvider)
            .subscribe { this.showToastError(R.string.common_network_error) }

        viewModel.intent(intent)
        viewModel.inputs.fetchAlbums()

        mRecyclerViewPaginator = RecyclerViewPaginator(homeRecyclerView, Action { viewModel.inputs.fetchNextAlbums() })
    }

    private fun initViews() {
        val layoutManager = GridLayoutManager(homeRecyclerView.context, 2)
        homeRecyclerView.layoutManager = layoutManager
        homeRecyclerView.itemAnimator = DefaultItemAnimator()

        mAlbumAdapter = AlbumAdapter(viewModel.inputs)
        homeRecyclerView.adapter = mAlbumAdapter
    }


}