package com.assessment.weatherflo.data.remote.dto.weather


import com.squareup.moshi.Json

data class Coord(
    @field:Json(name = "lat") val lat: Double = -1.0,
    @field:Json(name = "lon") val lon: Double = -1.0
)