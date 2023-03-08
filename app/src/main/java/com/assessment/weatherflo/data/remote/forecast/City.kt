package com.assessment.weatherflo.data.remote.forecast


import com.squareup.moshi.Json

data class City(
    @field:Json(name = "coord") val coord: Coord = Coord(),
    @field:Json(name = "country") val country: String = "",
    @field:Json(name = "id") val id: Int = -1,
    @field:Json(name = "name") val name: String = "",
    @field:Json(name = "population") val population: Int = -1,
    @field:Json(name = "sunrise") val sunrise: Long = -1,
    @field:Json(name = "sunset") val sunset: Long = -1,
    @field:Json(name = "timezone") val timezone: Int = -1
)