package ru.playsoftware.weather.data

import io.reactivex.Single

class WeatherRepository {

    private val service = WebService.create()

    fun getWeather() : Single<WeatherData> {
        return service.getWeather()
    }

}