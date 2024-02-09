package com.assessment.weatherflo.data.remote.dto.weather


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Snow(
    @field:Json(name = "1h") val h: Double = -1.0
)