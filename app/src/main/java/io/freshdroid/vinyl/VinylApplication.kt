package io.freshdroid.vinyl

import android.content.Context
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDexApplication
import io.freshdroid.vinyl.core.CoreComponent
import io.freshdroid.vinyl.core.CoreModule
import io.freshdroid.vinyl.core.DaggerCoreComponent
import io.freshdroid.vinyl.core.ui.BaseActivity
import io.freshdroid.vinyl.core.ui.BaseFragment
import timber.log.Timber

open class VinylApplication : MultiDexApplication(), LifecycleObserver {

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.builder()
            .coreModule(CoreModule(this))
            .build()
    }

    companion object {
        @JvmStatic
        fun coreComponent(context: Context) = (context.applicationContext as VinylApplication).coreComponent
    }

    fun coreComponent(): CoreComponent = coreComponent

    protected open fun isInUnitTests(): Boolean {
        return false
    }

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}

fun BaseActivity.coreComponent() = VinylApplication.coreComponent(this)

fun BaseFragment.coreComponent() = VinylApplication.coreComponent(requireNotNull(this.context))