package soup.movie.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import soup.movie.settings.TheaterSetting
import soup.movie.settings.ui.MainTabSetting
import javax.inject.Singleton

@Module
class SharedPreferencesModule {

    @Singleton
    @Provides
    fun provideMainTabSetting(preferences: SharedPreferences): MainTabSetting =
            MainTabSetting(preferences)

    @Singleton
    @Provides
    fun provideTheaterSetting(preferences: SharedPreferences): TheaterSetting =
            TheaterSetting(preferences)

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)
}