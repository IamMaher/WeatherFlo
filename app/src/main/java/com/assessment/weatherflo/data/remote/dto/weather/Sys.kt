package com.assessment.weatherflo.data.remote.dto.weather

import com.squareup.moshi.Json

data class Sys(
    @field:Json(name = "country") val country: String = "",
    @field:Json(name = "id") val id: Int = -1,
    @field:Json(name = "sunrise") val sunrise: Long = -1,
    @field:Json(name = "sunset") val sunset: Long = -1,
    @field:Json(name = "type") val type: Int = -1
)