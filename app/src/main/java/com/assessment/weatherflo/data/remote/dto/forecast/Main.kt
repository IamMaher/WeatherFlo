package com.assessment.weatherflo.data.remote.dto.forecast


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Main(
    @field:Json(name = "feels_like") val feelsLike: Double = -1.0,
    @field:Json(name = "grnd_level") val grndLevel: Int = -1,
    @field:Json(name = "humidity") val humidity: Int = -1,
    @field:Json(name = "pressure") val pressure: Int = -1,
    @field:Json(name = "sea_level") val seaLevel: Int = -1,
    @field:Json(name = "temp") val temp: Double = -1.0,
    @field:Json(name = "temp_kf") val tempKf: Double = -1.0,
    @field:Json(name = "temp_max") val tempMax: Double = -1.0,
    @field:Json(name = "temp_min") val tempMin: Double = -1.0
)