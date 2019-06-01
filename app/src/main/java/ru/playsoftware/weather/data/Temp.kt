package ru.playsoftware.weather.data

data class Temp (

    var day: Double = 0.toDouble(),
    var min: Double = 0.toDouble(),
    var max: Double = 0.toDouble(),
    var night: Double = 0.toDouble(),
    var eve: Double = 0.toDouble(),
    var morn: Double = 0.toDouble()

)
