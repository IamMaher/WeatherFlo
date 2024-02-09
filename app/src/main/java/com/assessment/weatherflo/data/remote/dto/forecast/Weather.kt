package com.assessment.weatherflo.data.remote.dto.forecast


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Weather(
    @field:Json(name = "description") val description: String = "",
    @field:Json(name = "icon") val icon: String = "",
    @field:Json(name = "id") val id: Int = -1,
    @field:Json(name = "main") val main: String = ""
)