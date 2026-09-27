package ru.netology.weatherapp

import ru.netology.weatherapp.util.WeatherUtils
import org.junit.Assert.*
import org.junit.Test

class WeatherUtilsTest {

    @Test
    fun `formatTemp positive adds plus sign`() {
        assertEquals("+5°", WeatherUtils.formatTemp(5.3))
        assertEquals("+10°", WeatherUtils.formatTemp(10.0))
    }

    @Test
    fun `formatTemp negative keeps minus`() {
        assertEquals("-5°", WeatherUtils.formatTemp(-5.3))
        assertEquals("-10°", WeatherUtils.formatTemp(-10.0))
    }

    @Test
    fun `formatTemp zero has no sign`() {
        assertEquals("0°", WeatherUtils.formatTemp(0.0))
        assertEquals("0°", WeatherUtils.formatTemp(0.4))
    }

    @Test
    fun `windDirectionToCompass known directions`() {
        assertEquals("N", WeatherUtils.windDirectionToCompass("north"))
        assertEquals("NE", WeatherUtils.windDirectionToCompass("northeast"))
        assertEquals("SW", WeatherUtils.windDirectionToCompass("southwest"))
    }

    @Test
    fun `windDirectionToCompass unknown returns input`() {
        assertEquals("XYZ", WeatherUtils.windDirectionToCompass("XYZ"))
        assertEquals("", WeatherUtils.windDirectionToCompass(""))
    }
}
