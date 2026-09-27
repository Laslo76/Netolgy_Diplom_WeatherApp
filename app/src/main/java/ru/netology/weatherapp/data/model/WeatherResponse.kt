package ru.netology.weatherapp.data.model

import com.google.gson.annotations.SerializedName

/**
* Top-level response from NGS Weather API:
*/
data class WeatherResponse(
    @SerializedName("metadata") val metadata: Metadata? = null,
    @SerializedName("forecasts") val forecasts: List<ForecastDay>? = null
)

data class Metadata(
    @SerializedName("resultset") val resultset: ResultSet? = null
)

data class ResultSet(
    @SerializedName("count") val count: Int = 0
)

/**
 * Прогноз на дату
 */
data class ForecastDay(
    @SerializedName("date") val date: String? = null,
    @SerializedName("temperature") val temperature: Double? = null,
    @SerializedName("pressure") val pressure: Double? = null,
    @SerializedName("humidity") val humidity: Double? = null,
    @SerializedName("hours") val hours: List<HourForecast>? = null
)

/**
 * Почасовой прогноз.
 */
data class HourForecast(
    @SerializedName("hour") val hour: Int? = null,
    @SerializedName("temperature") val temperature: TemperatureData? = null,
    @SerializedName("humidity") val humidity: HumidityData? = null,
    @SerializedName("pressure") val pressure: PressureData? = null,
    @SerializedName("wind") val wind: WindData? = null,
    @SerializedName("precipitation") val precipitation: PrecipitationData? = null
)

data class TemperatureData(
    @SerializedName("avg") val avg: Double? = null,
    @SerializedName("min") val min: Double? = null,
    @SerializedName("max") val max: Double? = null
)

data class HumidityData(
    @SerializedName("avg") val avg: Double? = null
)

data class PressureData(
    @SerializedName("avg") val avg: Double? = null
)

data class WindDataSpeed(
    @SerializedName("avg") val avg: Int = 0,
    @SerializedName("min") val min: Int = 0,
    @SerializedName("max") val max: Int = 0
)

data class WindDataDirection(
    @SerializedName("title") val title: String = "",
    @SerializedName("title_letter") val title_letter: String = "",
    @SerializedName("title_short") val title_short: String = "",
    @SerializedName("value") val value: String = ""
)

data class WindData(
    @SerializedName("speed") val speed: WindDataSpeed? = null,
    @SerializedName("direction") val direction: WindDataDirection? = null
)

data class PrecipitationData(
    @SerializedName("probability") val probability: Double? = null
)

/**
 * Данные по городам для:
 * GET http://pogoda.ngs.ru/api/v1/cities
 */
data class CitiesResponse(
    @SerializedName("metadata") val metadata: Metadata? = null,
    @SerializedName("cities") val cities: List<City>? = null
)

data class City(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("alias") val alias: String? = null,
    @SerializedName("title") val title: String? = null
)
