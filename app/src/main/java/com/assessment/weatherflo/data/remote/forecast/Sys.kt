package com.assessment.weatherflo.data.remote.forecast


import com.squareup.moshi.Json

data class Sys(
    @field:Json(name = "pod") val pod: String = ""
)