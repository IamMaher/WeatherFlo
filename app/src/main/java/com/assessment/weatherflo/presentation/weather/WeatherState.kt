package com.assessment.weatherflo.presentation.weather

import com.assessment.weatherflo.core.exception.Failure
import com.assessment.weatherflo.domain.weather.entity.WeatherRecord

data class WeatherState(
    val weatherInfo: WeatherRecord? = null,
    val isLoading: Boolean = false,
    val error: Failure? = null
)