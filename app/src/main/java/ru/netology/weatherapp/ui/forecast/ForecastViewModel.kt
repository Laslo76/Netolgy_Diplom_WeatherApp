package ru.netology.weatherapp.ui.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.netology.weatherapp.data.local.WeatherEntity
import ru.netology.weatherapp.data.prefs.SettingsManager
import ru.netology.weatherapp.data.repository.Resource
import ru.netology.weatherapp.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.netology.weatherapp.data.prefs.AppSettings
import javax.inject.Inject

data class ForecastUiState(
    val isLoading: Boolean = false,
    val forecasts: List<WeatherEntity> = emptyList(),
    val isFromCache: Boolean = false,
    val error: String? = null,
    val city: String = "moscow",
    val forecastDays: Int = 7
)

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val settingsManager: SettingsManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ForecastUiState(isLoading = true))
    val uiState: StateFlow<ForecastUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            // Подписываемся на ВСЕ изменения настроек (город, дни и т.д.)
            settingsManager.settingsFlow
                .collect { settings ->
                    // Как только настройки изменились — запускаем загрузку
                    loadForecast(settings)
                }
        }
    }

    private fun loadForecast(settings: AppSettings) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val city = settings.city
            val days = settings.forecastDays

            when (val result = repository.getForecast(city)) {
                is Resource.Success -> {
                    val forecasts = result.data?.take(days) ?: emptyList()
                    _uiState.value = ForecastUiState(
                        isLoading = false,
                        forecasts = forecasts,
                        isFromCache = result.isFromCache,
                        city = city,
                        forecastDays = days
                    )
                }
                else -> {
                    _uiState.value = ForecastUiState(
                        isLoading = false,
                        error = result.error,
                        city = city,
                        forecastDays = days
                    )
                }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            val settings = settingsManager.settingsFlow.first()
            loadForecast(settings)
        }
    }
}
