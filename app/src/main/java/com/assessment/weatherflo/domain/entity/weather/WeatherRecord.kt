package com.assessment.weatherflo.domain.entity.weather

import com.assessment.weatherflo.domain.entity.WeatherType

data class WeatherRecord(
    val time: String,
    val tempMax: Double,
    val tempMin: Double,
    val temp: String,
    val sunrise: String,
    val pressure: Int,
    val windSpeed: Double,
    val humidity: Int,
    val city: String,
    val weatherType: WeatherType
)