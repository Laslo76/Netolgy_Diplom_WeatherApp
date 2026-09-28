package ru.netology.weatherapp

import ru.netology.weatherapp.data.api.WeatherApiService
import ru.netology.weatherapp.data.local.WeatherDao
import ru.netology.weatherapp.data.local.WeatherEntity
import ru.netology.weatherapp.data.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.netology.weatherapp.data.model.ForecastDay
import ru.netology.weatherapp.data.model.HourForecast
import ru.netology.weatherapp.data.model.HumidityData
import ru.netology.weatherapp.data.model.Metadata
import ru.netology.weatherapp.data.model.PrecipitationData
import ru.netology.weatherapp.data.model.PressureData
import ru.netology.weatherapp.data.model.ResultSet
import ru.netology.weatherapp.data.model.TemperatureData
import ru.netology.weatherapp.data.model.WeatherResponse
import ru.netology.weatherapp.data.model.WindData
import ru.netology.weatherapp.data.model.WindDataDirection
import ru.netology.weatherapp.data.model.WindDataSpeed

class WeatherRepositoryTest {

    private lateinit var repository: WeatherRepository
    private val apiService: WeatherApiService = mockk()
    private val dao: WeatherDao = mockk(relaxed = true)

    @Before
    fun setup() {
        repository = WeatherRepository(apiService, dao)
    }

    @Test
    fun `getForecast success returns data from API`() = runTest {
        // Arrange
        val apiResponse = WeatherResponse(
            metadata = Metadata(resultset = ResultSet(count = 3)),
            forecasts = listOf(
                ForecastDay(
                    date = "2024-01-15",
                    temperature = -5.0,
                    pressure = 750.0,
                    humidity = 80.0,
                    hours = listOf(
                        HourForecast(
                            hour = 0,
                            temperature = TemperatureData(avg = -10.0, min = -12.0, max = -8.0),
                            humidity = HumidityData(avg = 85.0),
                            pressure = PressureData(avg = 750.0),
                            wind = WindData(speed = WindDataSpeed(3, 2, 4),
                                direction = WindDataDirection("NW", "NW","NW","NW")),
                            precipitation = PrecipitationData(probability = 20.0)
                        ),
                        HourForecast(
                            hour = 6,
                            temperature = TemperatureData(avg = -8.0, min = -10.0, max = -6.0),
                            humidity = HumidityData(avg = 80.0),
                            pressure = PressureData(avg = 751.0),
                            wind = WindData(speed = WindDataSpeed(2, 1,3),
                                direction = WindDataDirection("N", "N","N","N")),
                            precipitation = PrecipitationData(probability = 10.0)
                        ),
                        HourForecast(
                            hour = 12,
                            temperature = TemperatureData(avg = -5.0, min = -7.0, max = -3.0),
                            humidity = HumidityData(avg = 70.0),
                            pressure = PressureData(avg = 750.0),
                            wind = WindData(speed = WindDataSpeed(4, 1,7),
                                direction = WindDataDirection("NE", "NE","NE","NE")),
                            precipitation = PrecipitationData(probability = 30.0)
                        )
                    )
                ),
                ForecastDay(
                    date = "2024-01-16",
                    temperature = -3.0,
                    pressure = 748.0,
                    humidity = 75.0,
                    hours = listOf(
                        HourForecast(
                            hour = 12,
                            temperature = TemperatureData(avg = -3.0, min = -5.0, max = 0.0),
                            humidity = HumidityData(avg = 75.0),
                            pressure = PressureData(avg = 748.0),
                            wind = WindData(speed = WindDataSpeed(5, 4,6),
                                direction = WindDataDirection("SE", "SE","SE","SE")),
                            precipitation = PrecipitationData(probability = 40.0)
                        )
                    )
                )
            )
        )

        coEvery { apiService.getForecast("moscow") } returns apiResponse
        coEvery { dao.getForecastForCity("moscow") } returns emptyList()

        // Act
        val result = repository.getForecast("moscow")

        // Assert
        assertTrue( "Ожидался Success, но был: $result", result.data != null && result.error == null,)
        assertFalse(result.isFromCache)
        assertEquals(2, result.data?.size)

        val firstDay = result.data!![0]
        assertEquals("2024-01-15", firstDay.date)
        assertEquals("moscow", firstDay.city)
        // Index 2 is the "day" hour
        assertEquals(-5.0, firstDay.tempAvg, 0.01)
        assertEquals(-7.0, firstDay.tempMin, 0.01)
        assertEquals(-3.0, firstDay.tempMax, 0.01)
        assertEquals(70.0, firstDay.humidity, 0.01)
        assertEquals(750.0, firstDay.pressure, 0.01)
        assertEquals("NE", firstDay.windDirection)
        assertEquals(30.0, firstDay.precipitationProbability, 0.01)

        // Verify cache operations
        coVerify { dao.deleteForCity("moscow") }
        coVerify { dao.insertAll(any()) }
    }

    @Test
    fun `getForecast network failure returns cached data`() = runTest {
        // Arrange
        val cachedData = listOf(
            WeatherEntity(
                date = "2026-09-15",
                city = "moscow",
                tempAvg = -5.0,
                tempMin = -10.0,
                tempMax = 0.0,
                humidity = 80.0,
                pressure = 750.0,
                windSpeed = 3,
                windDirection = "NW",
                precipitationProbability = 20.0,
                cachedAt = System.currentTimeMillis()
            )
        )
        coEvery { apiService.getForecast("moscow") } throws RuntimeException("Network error")
        coEvery { dao.getForecastForCity("moscow") } returns cachedData

        // Act
        val result = repository.getForecast("moscow")

        // Assert
        assertTrue( "Ожидался Success, но был: $result", result.data != null && result.error == null,)
        assertTrue(result.isFromCache)
        assertEquals(1, result.data?.size)
        assertEquals("2026-09-15", result.data!![0].date)
    }

    @Test
    fun `getForecast network failure no cache returns error`() = runTest {
        // Arrange
        coEvery { apiService.getForecast("moscow") } throws RuntimeException("Network error")
        coEvery { dao.getForecastForCity("moscow") } returns emptyList()

        // Act
        val result = repository.getForecast("moscow")

        // Assert
        assertTrue( "Ожидался Error, но был: $result", result.error != null)
        assertNull(result.data)
        assertNotNull(result.error)
    }

    @Test
    fun `mapResponseToEntities handles null fields gracefully`() {
        // Arrange
        val response = WeatherResponse(
            forecasts = listOf(
                ForecastDay(
                    date = "2024-01-15",
                    temperature = null,
                    pressure = null,
                    humidity = null,
                    hours = null
                )
            )
        )

        // Act
        val entities = repository.mapResponseToEntities(response, "moscow")

        // Assert
        assertEquals(1, entities.size)
        assertEquals("2024-01-15", entities[0].date)
        assertEquals(0.0, entities[0].tempAvg, 0.01)
        assertEquals(0.0, entities[0].humidity, 0.01)
    }

    @Test
    fun `mapResponseToEntities empty forecasts returns empty list`() {
        // Arrange
        val response = WeatherResponse(forecasts = emptyList())

        // Act
        val entities = repository.mapResponseToEntities(response, "moscow")

        // Assert
        assertTrue(entities.isEmpty())
    }

    @Test
    fun `mapResponseToEntities null forecasts returns empty list`() {
        // Arrange
        val response = WeatherResponse(forecasts = null)

        // Act
        val entities = repository.mapResponseToEntities(response, "moscow")

        // Assert
        assertTrue(entities.isEmpty())
    }
}