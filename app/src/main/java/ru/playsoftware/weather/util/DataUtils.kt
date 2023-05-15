package ru.playsoftware.weather.util

import ru.playsoftware.weather.data.Day
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

fun getWeatherInfo(day: Day): String {
    return getDate(day.dt * 1000L, "dd.MM.yyyy") +
            " " + day.temp.day.roundToInt().toString() +
            "\u2103, " + day.weather[0].description
}

fun getDate(milliSeconds: Long, dateFormat: String): String {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = milliSeconds
    return formatter.format(calendar.time)
}