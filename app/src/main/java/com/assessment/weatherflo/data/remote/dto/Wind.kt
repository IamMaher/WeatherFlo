package com.assessment.weatherflo.data.remote.dto


import com.squareup.moshi.Json

data class Wind(
    @field:Json(name = "deg") val deg: Int = -1,
    @field:Json(name = "gust") val gust: Double = -1.0,
    @field:Json(name = "speed") val speed: Double = -1.0
)