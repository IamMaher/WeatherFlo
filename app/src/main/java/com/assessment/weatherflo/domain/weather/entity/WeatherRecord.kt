package com.assessment.weatherflo.domain.weather.entity

import java.util.Date

data class WeatherRecord(
    val time: Date,
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