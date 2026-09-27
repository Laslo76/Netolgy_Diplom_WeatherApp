package ru.netology.weatherapp.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.netology.weatherapp.data.prefs.AppSettings
import ru.netology.weatherapp.data.prefs.SettingsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsUiState(
    val settings: AppSettings = AppSettings(),
    val isLoading: Boolean = false
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsManager: SettingsManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState(isLoading = true))
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            val settings = settingsManager.settingsFlow.first()
            _uiState.value = SettingsUiState(settings = settings, isLoading = false)
        }
    }

    fun setCity(city: String) {
        viewModelScope.launch {
            settingsManager.setCity(city)
            _uiState.value = _uiState.value.copy(
                settings = _uiState.value.settings.copy(city = city)
            )
        }
    }

    fun setForecastDays(days: Int) {
        viewModelScope.launch {
            settingsManager.setForecastDays(days)
            _uiState.value = _uiState.value.copy(
                settings = _uiState.value.settings.copy(forecastDays = days)
            )
        }
    }

    fun setThemeMode(mode: String) {
        viewModelScope.launch {
            settingsManager.setThemeMode(mode)
            _uiState.value = _uiState.value.copy(
                settings = _uiState.value.settings.copy(themeMode = mode)
            )
        }
    }

}
