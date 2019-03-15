package io.freshdroid.vinyl

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.android.lifecycle.test.TestLifecycleOwner
import io.freshdroid.vinyl.core.CoreComponent
import junit.framework.TestCase
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


@RunWith(RobolectricGradleTestRunner::class)
@Config(application = VinylApplicationTest::class, shadows = [ShadowAndroidXMultiDex::class])
internal abstract class RobolectricTestCase : TestCase() {

    private var mApplication: VinylApplicationTest? = null

    protected fun application(): VinylApplicationTest {
        if (mApplication != null) {
            return mApplication as VinylApplicationTest
        }

        mApplication = ApplicationProvider.getApplicationContext() as VinylApplicationTest
        return mApplication as VinylApplicationTest
    }

    protected fun context(): Context {
        return application().applicationContext
    }

    protected fun coreComponent(): CoreComponent {
        return application().coreComponent()
    }

    protected fun scopeProvider(): AndroidLifecycleScopeProvider {
        val testLifecycleOwner = TestLifecycleOwner.create()
        return AndroidLifecycleScopeProvider.from(testLifecycleOwner)
    }

}