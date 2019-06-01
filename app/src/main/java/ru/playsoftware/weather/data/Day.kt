package ru.playsoftware.weather.data

data class Day (

    var dt: Int = 0,
    var temp: Temp,
    var pressure: Double = 0.toDouble(),
    var humidity: Int = 0,
    var weather: List<Weather>,
    var speed: Double = 0.toDouble(),
    var deg: Int = 0,
    var clouds: Int = 0,
    var rain: Double = 0.toDouble()

)
