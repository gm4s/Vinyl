package io.freshdroid.vinyl.core.preferences

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import io.freshdroid.vinyl.core.qualifiers.AccessTokenPreference
import io.freshdroid.vinyl.core.qualifiers.UserPreference
import javax.inject.Singleton

@Module
class PreferencesModule {

    @Provides
    @Singleton
    @UserPreference
    fun provideUserPreference(sharedPreferences: SharedPreferences): StringPreferenceType {
        return StringPreference(sharedPreferences, SharedPreferenceKey.USER)
    }

    @Provides
    @Singleton
    @AccessTokenPreference
    fun provideAccessTokenPreference(sharedPreferences: SharedPreferences): StringPreferenceType {
        return StringPreference(sharedPreferences, SharedPreferenceKey.ACCESS_TOKEN)
    }

}