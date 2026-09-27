package ru.netology.weatherapp.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.netology.weatherapp.R
import ru.netology.weatherapp.data.local.WeatherEntity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object WeatherUtils {

    private val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val outputDayFormat = SimpleDateFormat("EEE, dd MMM", Locale.getDefault())

    /**
     * Преобразование строки в объект Date
     */
    fun parseDate(dateStr: String): Date? {
        return try {
            inputFormat.parse(dateStr)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Формат даты для отображения.
     */
    @Composable
    fun formatDate(dateStr: String): String {
        val date = parseDate(dateStr) ?: return dateStr
        val today = Calendar.getInstance()
        val target = Calendar.getInstance().apply { time = date }

        if (today.get(Calendar.YEAR) == target.get(Calendar.YEAR) &&
            today.get(Calendar.DAY_OF_YEAR) == target.get(Calendar.DAY_OF_YEAR)) {
            return stringResource(R.string.today)
        }

        val tomorrow = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, 1) }
        if (tomorrow.get(Calendar.YEAR) == target.get(Calendar.YEAR) &&
            tomorrow.get(Calendar.DAY_OF_YEAR) == target.get(Calendar.DAY_OF_YEAR)) {
            return stringResource(R.string.tomorrow)
        }

        return outputDayFormat.format(date)
    }

    /**
     * Короткое имя для строки даты.
     */
    fun getDayName(dateStr: String): String {
        val date = parseDate(dateStr) ?: return dateStr
        return SimpleDateFormat("EEE", Locale.getDefault()).format(date)
    }

    /**
     * Возвращает описание погодных условий на основе температуры и количества выпавших осадков.
     */
    fun getWeatherCondition(entity: WeatherEntity): String {
        return when {
            entity.precipitationProbability > 70 -> "Rain"
            entity.precipitationProbability > 30 -> "Cloudy"
            entity.tempAvg < 0 -> "Cold"
            else -> "Clear"
        }
    }

    /**
     * Направление ветра.
     */
    fun windDirectionToCompass(direction: String): String {
        return when (direction.lowercase()) {
            "n", "north" -> "N"
            "ne", "northeast" -> "NE"
            "e", "east" -> "E"
            "se", "southeast" -> "SE"
            "s", "south" -> "S"
            "sw", "southwest" -> "SW"
            "w", "west" -> "W"
            "nw", "northwest" -> "NW"
            else -> direction
        }
    }

    /**
     * Отформатировать температуру со знаком.
     */
    fun formatTemp(temp: Double): String {
        val rounded = Math.round(temp).toInt()
        return if (rounded > 0) "+${rounded}°" else "${rounded}°"
    }

    /**
     * Отформатировать температуру без знака градуса.
     */
    fun formatTempValue(temp: Double): String {
        val rounded = Math.round(temp).toInt()
        return rounded.toString()
    }
}
