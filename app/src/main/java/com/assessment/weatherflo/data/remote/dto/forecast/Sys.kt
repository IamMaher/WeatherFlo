package com.assessment.weatherflo.data.remote.dto.forecast


import com.squareup.moshi.Json

data class Sys(
    @field:Json(name = "pod") val pod: String = ""
)