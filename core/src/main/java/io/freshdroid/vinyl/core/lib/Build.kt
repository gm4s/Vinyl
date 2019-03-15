package io.freshdroid.vinyl.core.lib

import android.content.pm.PackageInfo
import io.freshdroid.vinyl.core.BuildConfig


class Build(
        private val packageInfo: PackageInfo
) {

    fun applicationId(): String {
        return packageInfo.packageName
    }

    fun isDebug(): Boolean {
        return BuildConfig.DEBUG
    }

    fun isRelease(): Boolean {
        return !BuildConfig.DEBUG
    }

    @Suppress("DEPRECATION")
    fun versionCode(): Int? {
        return packageInfo.versionCode
    }

    fun versionName(): String {
        return packageInfo.versionName
    }

    fun firstInstallTime(): Long {
        return packageInfo.firstInstallTime
    }

}