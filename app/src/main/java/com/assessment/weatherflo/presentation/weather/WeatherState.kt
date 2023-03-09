package com.assessment.weatherflo.presentation.weather

import com.assessment.weatherflo.core.exception.Failure
import com.assessment.weatherflo.core.functional.Units
import com.assessment.weatherflo.domain.entity.weather.WeatherRecord

data class WeatherState(
    val isRequestPermission: Boolean = false,
    val units: String = Units.Metric.value,
    val data: WeatherRecord? = null,
    val isLoading: Boolean = false,
    val error: Failure? = null
)