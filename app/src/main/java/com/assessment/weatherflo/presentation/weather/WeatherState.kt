package com.assessment.weatherflo.presentation.weather

import com.assessment.weatherflo.core.exception.Failure
import com.assessment.weatherflo.domain.entity.weather.WeatherRecord

data class WeatherState(
    val data: WeatherRecord? = null,
    val isLoading: Boolean = false,
    val error: Failure? = null
)