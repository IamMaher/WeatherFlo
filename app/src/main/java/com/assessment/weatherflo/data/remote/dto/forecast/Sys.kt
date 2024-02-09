package com.assessment.weatherflo.data.remote.dto.forecast


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Sys(
    @field:Json(name = "pod") val pod: String = ""
)