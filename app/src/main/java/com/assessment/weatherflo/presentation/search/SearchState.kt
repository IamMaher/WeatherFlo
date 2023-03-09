package com.assessment.weatherflo.presentation.search

import com.assessment.weatherflo.core.exception.Failure
import com.assessment.weatherflo.domain.entity.cities.CityEntity

data class SearchState(
    val listResult: List<CityEntity> = emptyList(),
    val listHistory: List<CityEntity> = emptyList(),
    val listPlaceholder: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val error: Failure? = null
)