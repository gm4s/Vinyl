package io.freshdroid.vinyl.features.album.views

import android.os.Bundle
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProviders
import com.uber.autodispose.autoDisposable
import io.freshdroid.vinyl.R
import io.freshdroid.vinyl.core.extensions.bind
import io.freshdroid.vinyl.core.rx.transformers.Transformers.observeForUI
import io.freshdroid.vinyl.core.ui.BaseActivity
import io.freshdroid.vinyl.core.ui.TransitionUtils.slideOutRight
import io.freshdroid.vinyl.features.album.viewmodels.AlbumDetailsViewModel

class AlbumDetailsActivity : BaseActivity() {

    companion object {
        const val KEY_ALBUM = "KEY_ALBUM"
    }

    private val viewModelFactory by lazy { AlbumDetailsViewModel.Factory(scopeProvider) }
    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(AlbumDetailsViewModel::class.java)
    }

    private val albumBackFrameLayout: FrameLayout by bind(R.id.albumBackFrameLayout)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_details)

        viewModel.outputs.back()
            .compose(observeForUI())
            .autoDisposable(scopeProvider)
            .subscribe { this.back() }

        viewModel.intent(intent)
        albumBackFrameLayout.setOnClickListener { viewModel.inputs.onBackClick() }
    }

    override fun exitTransition(): Pair<Int, Int>? {
        return slideOutRight()
    }

}