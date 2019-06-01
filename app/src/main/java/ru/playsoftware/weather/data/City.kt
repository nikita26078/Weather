package ru.playsoftware.weather.data

data class City (

    var id: Int = 0,
    var name: String,
    var coord: Coord,
    var country: String,
    var population: Int = 0

)
