package com.assessment.weatherflo.data.remote.forecast


import com.squareup.moshi.Json

data class Weather(
    @field:Json(name = "description") val description: String = "",
    @field:Json(name = "icon") val icon: String = "",
    @field:Json(name = "id") val id: Int = -1,
    @field:Json(name = "main") val main: String = ""
)