package ru.playsoftware.weather.ui.main

import androidx.lifecycle.ViewModel
import io.reactivex.Single
import ru.playsoftware.weather.data.WeatherData
import ru.playsoftware.weather.data.WeatherRepository

class MainViewModel internal constructor(
        repository: WeatherRepository
): ViewModel() {

    val weatherData : Single<WeatherData> = repository.getWeather()

}
