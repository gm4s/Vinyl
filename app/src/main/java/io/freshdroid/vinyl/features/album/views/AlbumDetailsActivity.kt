package io.freshdroid.vinyl.features.album.views

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.appbar.AppBarLayout
import com.uber.autodispose.autoDisposable
import io.freshdroid.vinyl.R
import io.freshdroid.vinyl.commons.extensions.loadPicture
import io.freshdroid.vinyl.commons.models.Album
import io.freshdroid.vinyl.core.extensions.bind
import io.freshdroid.vinyl.core.extensions.getBitmap
import io.freshdroid.vinyl.core.rx.transformers.Transformers.observeForUI
import io.freshdroid.vinyl.core.ui.BaseActivity
import io.freshdroid.vinyl.core.ui.TransitionUtils.slideOutRight
import io.freshdroid.vinyl.core.utils.AnimUtils
import io.freshdroid.vinyl.core.utils.ColorUtils
import io.freshdroid.vinyl.core.utils.ViewUtils
import io.freshdroid.vinyl.features.album.viewmodels.AlbumDetailsViewModel

class AlbumDetailsActivity : BaseActivity() {

    companion object {
        const val KEY_ALBUM = "KEY_ALBUM"

        private const val SCRIM_ADJUSTMENT = 0.075f
    }

    private val viewModelFactory by lazy { AlbumDetailsViewModel.Factory(scopeProvider) }
    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(AlbumDetailsViewModel::class.java)
    }

    private val albumDetailsContainerCoordinatorLayout: CoordinatorLayout by bind(R.id.albumDetailsContainerCoordinatorLayout)
    private val appBarLayout: AppBarLayout by bind(R.id.appBarLayout)
    private val albumBackFrameLayout: FrameLayout by bind(R.id.albumBackFrameLayout)
    private val albumIconBackImageView: ImageView by bind(R.id.albumIconBackImageView)
    private val albumCoverImageView: ImageView by bind(R.id.albumCoverImageView)
    private val albumTitleTextView: TextView by bind(R.id.albumTitleTextView)
    private val artistNameTextView: TextView by bind(R.id.artistNameTextView)
    private val trackNumberTextView: TextView by bind(R.id.trackNumberTextView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_details)

        viewModel.outputs.back()
            .compose(observeForUI())
            .autoDisposable(scopeProvider)
            .subscribe { this.back() }

        viewModel.outputs.album()
            .compose(observeForUI())
            .autoDisposable(scopeProvider)
            .subscribe(this::displayAlbumDetails)

        viewModel.intent(intent)
        albumBackFrameLayout.setOnClickListener { viewModel.inputs.onBackClick() }
    }

    override fun exitTransition(): Pair<Int, Int>? {
        return slideOutRight()
    }

    private fun displayAlbumDetails(album: Album) {
        val layoutParams = appBarLayout.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.height = albumCoverImageView.resources.displayMetrics.widthPixels
        appBarLayout.layoutParams = layoutParams

        album.picture?.let { albumCoverImageView.loadPicture(it, profilePictureLoadListener) }
        albumTitleTextView.text = album.title
        artistNameTextView.text = String.format(getString(R.string.album_by), album.artist?.name)
        trackNumberTextView.text = getTrackNumberText(album)
    }

    private fun getTrackNumberText(album: Album): String {
        return if (album.nbTracks != null) {
            if (album.nbTracks > 1)
                String.format(getString(R.string.number_track_plural), album.nbTracks)
            else getString(R.string.number_track)
        } else ""
    }

    private val profilePictureLoadListener = object : RequestListener<Drawable> {

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onResourceReady(
            resource: Drawable,
            model: Any,
            target: Target<Drawable>,
            dataSource: DataSource,
            isFirstResource: Boolean
        ): Boolean {
            val bitmap = resource.getBitmap() ?: return false

            val twentyFourDip = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                24f,
                this@AlbumDetailsActivity.resources.displayMetrics
            ).toInt()

            Palette.from(bitmap)
                .maximumColorCount(3)
                .clearFilters()
                .setRegion(0, 0, bitmap.width - 1, twentyFourDip)
                /* - 1 to work around https://code.google.com/p/android/issues/detail?id=191013 */
                .generate { palette -> palette?.let { applyTopPalette(bitmap, it) } }

            return false
        }

        override fun onLoadFailed(
            e: GlideException?,
            model: Any,
            target: Target<Drawable>,
            isFirstResource: Boolean
        ) = false
    }

    @SuppressLint("WrongConstant")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun applyTopPalette(bitmap: Bitmap, palette: Palette) {
        val lightness = ColorUtils.isDark(palette)
        val isDark = if (lightness == ColorUtils.LIGHTNESS_UNKNOWN) {
            ColorUtils.isDark(bitmap, bitmap.width / 2, 0)
        } else {
            lightness == ColorUtils.IS_DARK
        }

        if (isDark) {
            albumIconBackImageView.setColorFilter(
                ContextCompat.getColor(this@AlbumDetailsActivity, io.freshdroid.vinyl.R.color.light_grey)
            )
        }

        var statusBarColor = window.statusBarColor
        ColorUtils.getMostPopulousSwatch(palette)?.let {
            statusBarColor = ColorUtils.scrimify(it.rgb, isDark, SCRIM_ADJUSTMENT)
            if (!isDark) {
                ViewUtils.setLightStatusBar(albumDetailsContainerCoordinatorLayout)
            }
        }

        if (statusBarColor != window.statusBarColor) {
            ValueAnimator.ofArgb(window.statusBarColor, statusBarColor).apply {
                addUpdateListener { animation ->
                    window.statusBarColor = animation.animatedValue as Int
                }
                duration = 400L
                interpolator = AnimUtils.getFastOutSlowInInterpolator(this@AlbumDetailsActivity)
            }.start()
        }
    }

}