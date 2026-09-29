package ru.netology.weatherapp.data.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

data class AppSettings(
    val city: String = "moscow",
    val forecastDays: Int = 7,
    val themeMode: String = "light",
    val language: String = "system"
)

@Singleton
class SettingsManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val CITY_KEY = stringPreferencesKey("city")
    private val DAYS_KEY = intPreferencesKey("forecast_days")
    private val THEME_KEY = stringPreferencesKey("theme_mode")
    private val LANGUAGE_KEY = stringPreferencesKey("language")

    val settingsFlow: Flow<AppSettings> = context.dataStore.data.map { prefs ->
        AppSettings(
            city = prefs[CITY_KEY] ?: "moscow",
            forecastDays = prefs[DAYS_KEY] ?: 7,
            themeMode = prefs[THEME_KEY] ?: "system",
            language = prefs[LANGUAGE_KEY] ?: "system"
        )
    }

    suspend fun setCity(city: String) {
        context.dataStore.edit { it[CITY_KEY] = city }
    }


    suspend fun setForecastDays(days: Int) {
        context.dataStore.edit { it[DAYS_KEY] = days }
    }

    suspend fun setThemeMode(mode: String) {
        context.dataStore.edit { it[THEME_KEY] = mode }
    }
}
