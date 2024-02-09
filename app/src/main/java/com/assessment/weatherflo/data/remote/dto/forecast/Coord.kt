package com.assessment.weatherflo.data.remote.dto.forecast


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Coord(
    @field:Json(name = "lat") val lat: Double = -1.0,
    @field:Json(name = "lon") val lon: Double = -1.0
)