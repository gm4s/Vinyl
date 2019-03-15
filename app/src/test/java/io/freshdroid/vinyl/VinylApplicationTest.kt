package io.freshdroid.vinyl


internal class VinylApplicationTest : VinylApplication() {

    override fun isInUnitTests(): Boolean = true

    override fun onCreate() {
        super.onCreate()
    }

}