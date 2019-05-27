package soup.movie.data

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.BuildCompat
import soup.movie.data.model.ThemeOption
import soup.movie.settings.impl.ThemeOptionSetting

class ThemeOptionManager(
    private val setting: ThemeOptionSetting
) {

    fun initialize() {
        AppCompatDelegate.setDefaultNightMode(setting.get().nightMode)
    }

    fun apply(themeOption: ThemeOption) {
        setting.set(themeOption)
        AppCompatDelegate.setDefaultNightMode(themeOption.nightMode)
    }

    fun createOptions(): List<ThemeOption> {
        return if (BuildCompat.isAtLeastQ()) {
            createOptionsAtLeastQ()
        } else {
            createOptionsAtPreQ()
        }
    }

    private fun createOptionsAtLeastQ(): List<ThemeOption> {
        return listOf(
            ThemeOption.Light,
            ThemeOption.Dark,
            ThemeOption.System
        )
    }

    private fun createOptionsAtPreQ(): List<ThemeOption> {
        return listOf(
            ThemeOption.Light,
            ThemeOption.Dark,
            ThemeOption.Battery
        )
    }

    companion object {

        private val ThemeOption.nightMode: Int
            get() = when (this) {
                ThemeOption.Light -> AppCompatDelegate.MODE_NIGHT_NO
                ThemeOption.Dark -> AppCompatDelegate.MODE_NIGHT_YES
                ThemeOption.System -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                ThemeOption.Battery -> AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
            }

        val defaultThemeOption: ThemeOption
            get() = if (BuildCompat.isAtLeastQ()) {
                ThemeOption.System
            } else {
                ThemeOption.Battery
            }
    }
}
