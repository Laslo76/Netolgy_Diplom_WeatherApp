package ru.netology.weatherapp.data.local

import androidx.room.*

@Dao
interface WeatherDao {

    @Query("SELECT * FROM forecast_days WHERE city = :city ORDER BY date ASC")
    suspend fun getForecastForCity(city: String): List<WeatherEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(forecasts: List<WeatherEntity>)

    @Query("DELETE FROM forecast_days WHERE city = :city")
    suspend fun deleteForCity(city: String)

    @Query("SELECT MAX(cachedAt) FROM forecast_days WHERE city = :city")
    suspend fun getLastCacheTime(city: String): Long?
}
