package ru.netology.weatherapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "forecast_days")
data class WeatherEntity(
    @PrimaryKey val date: String,
    val city: String,
    val tempAvg: Double,
    val tempMin: Double,
    val tempMax: Double,
    val humidity: Double,
    val pressure: Double,
    val windSpeed: Int,
    val windDirection: String,
    val precipitationProbability: Double,
    val cachedAt: Long
)
