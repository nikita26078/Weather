package ru.playsoftware.weather.data

data class WeatherData (

    var city: City,
    var cod: String,
    var message: Double = 0.toDouble(),
    var cnt: Int = 0,
    var list: List<Day>

)
