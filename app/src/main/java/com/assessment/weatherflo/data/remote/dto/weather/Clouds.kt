package com.assessment.weatherflo.data.remote.dto.weather


import com.squareup.moshi.Json

data class Clouds(
    @field:Json(name = "all") val all: Int = -1
)