package ru.netology.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import ru.netology.weatherapp.data.prefs.SettingsManager
import ru.netology.weatherapp.ui.navigation.AppNavigation
import ru.netology.weatherapp.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.netology.weatherapp.data.prefs.AppSettings
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var settingsManager: SettingsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Read theme setting
            val settingsState = settingsManager.settingsFlow.collectAsState(
                initial = AppSettings()
            )
            val settings by settingsState

            val darkTheme = when (settings.themeMode) {
                "light" -> false
                "dark" -> true
                else -> isSystemInDarkTheme()
            }

            WeatherAppTheme(darkTheme = darkTheme) {
                AppNavigation()
            }
        }

        // Применить языковую настройку
        lifecycleScope.launch {
            val settings = settingsManager.settingsFlow.first()
            applyLanguage(settings.language)
        }
    }

    private fun applyLanguage(langCode: String) {
        if (langCode == "system") return
        val locale = Locale(langCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}
