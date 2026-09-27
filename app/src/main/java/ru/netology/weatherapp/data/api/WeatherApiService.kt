package ru.netology.weatherapp.data.api

import ru.netology.weatherapp.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("api/v1/forecasts/forecast")
    suspend fun getForecast(
        @Query("city") city: String
    ): WeatherResponse
}
