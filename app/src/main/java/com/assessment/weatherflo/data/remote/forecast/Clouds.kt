package com.assessment.weatherflo.data.remote.forecast


import com.squareup.moshi.Json

data class Clouds(
    @field:Json(name = "all") val all: Int = -1
)