package com.assessment.weatherflo.domain.entity.forecast

data class ForecastRecord(
    val city: String,
    val weatherList: List<ForecastData>,
)