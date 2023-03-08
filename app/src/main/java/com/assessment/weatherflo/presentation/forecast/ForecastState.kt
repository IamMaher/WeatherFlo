package com.assessment.weatherflo.presentation.forecast

import com.assessment.weatherflo.core.exception.Failure
import com.assessment.weatherflo.domain.entity.forecast.ForecastRecord

data class ForecastState(
    val data: ForecastRecord? = null,
    val isLoading: Boolean = false,
    val error: Failure? = null
)