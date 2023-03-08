package com.assessment.weatherflo.domain.entity.forecast

import com.assessment.weatherflo.domain.entity.WeatherType

data class ForecastData(
    val time: Long,
    val tempMax: Double,
    val tempMin: Double,
    val temp: String,
    val pressure: Int,
    val windSpeed: Double,
    val humidity: Int,
    val weatherType: WeatherType,
    val isExpanded: Boolean = false
)
