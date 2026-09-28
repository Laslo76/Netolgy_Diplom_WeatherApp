package ru.netology.weatherapp.data.repository

import ru.netology.weatherapp.data.api.WeatherApiService
import ru.netology.weatherapp.data.local.WeatherDao
import ru.netology.weatherapp.data.local.WeatherEntity
import ru.netology.weatherapp.data.model.WeatherResponse
import javax.inject.Inject
import javax.inject.Singleton

sealed class Resource<T>(
    val data: T? = null,
    val error: String? = null,
    val isFromCache: Boolean = false
) {
    class Success<T>(data: T?, isFromCache: Boolean = false) :
        Resource<T>(data = data, isFromCache = isFromCache)

    class Error<T>(message: String?) :
        Resource<T>(error = message)
}

@Singleton
class WeatherRepository @Inject constructor(
    private val apiService: WeatherApiService,
    private val dao: WeatherDao
) {

    suspend fun getForecast(city: String): Resource<List<WeatherEntity>> {
        return try {
            val response = apiService.getForecast(city)
            val entities = mapResponseToEntities(response, city)
            dao.deleteForCity(city)
            dao.insertAll(entities)
            Resource.Success(data = entities, isFromCache = false)
        } catch (e: Exception) {
            val cached = dao.getForecastForCity(city)
            if (cached.isNotEmpty()) {
                Resource.Success(data = cached, isFromCache = true)
            } else {
                Resource.Error(message = e.message ?: "Unknown error")
            }
        }
    }

    fun mapResponseToEntities(
        response: WeatherResponse,
        city: String
    ): List<WeatherEntity> {
        val forecasts = response.forecasts ?: return emptyList()
        return forecasts.map { day ->
            val dayHour = day.hours?.getOrNull(2) ?: day.hours?.firstOrNull()

            WeatherEntity(
                date = day.date ?: "",
                city = city,
                tempAvg = dayHour?.temperature?.avg ?: day.temperature ?: 0.0,
                tempMin = dayHour?.temperature?.min
                    ?: day.hours?.minOfOrNull { it.temperature?.min ?: Double.MAX_VALUE }
                    ?: 0.0,
                tempMax = dayHour?.temperature?.max
                    ?: day.hours?.maxOfOrNull { it.temperature?.max ?: Double.MIN_VALUE }
                    ?: 0.0,
                humidity = dayHour?.humidity?.avg ?: day.humidity ?: 0.0,
                pressure = dayHour?.pressure?.avg ?: day.pressure ?: 0.0,
                windSpeed = dayHour?.wind?.speed?.avg ?: 0,
                windDirection = dayHour?.wind?.direction?.title ?: "",
                precipitationProbability = dayHour?.precipitation?.probability ?: 0.0,
                cachedAt = System.currentTimeMillis()
            )
        }
    }

    //suspend fun getLastCacheTime(city: String): Long? = dao.getLastCacheTime(city)
}