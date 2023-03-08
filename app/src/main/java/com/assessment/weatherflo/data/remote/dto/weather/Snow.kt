package com.assessment.weatherflo.data.remote.dto.weather


import com.squareup.moshi.Json

data class Snow(
    @field:Json(name = "1h") val h: Double = -1.0
)