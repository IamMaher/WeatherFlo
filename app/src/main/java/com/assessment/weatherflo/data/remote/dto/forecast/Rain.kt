package com.assessment.weatherflo.data.remote.dto.forecast


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Rain(
    @field:Json(name = "3h") val h: Double = -1.0
)