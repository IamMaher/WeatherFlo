package com.assessment.weatherflo.domain.usecase

import com.assessment.weatherflo.core.exception.Failure
import com.assessment.weatherflo.core.functional.Either
import com.assessment.weatherflo.core.interactor.UseCase
import com.assessment.weatherflo.data.repository.Repository
import com.assessment.weatherflo.domain.entity.weather.WeatherRecord
import javax.inject.Inject

class WeatherUseCase @Inject constructor(private val repository: Repository) :
    UseCase<WeatherRecord, WeatherUseCase.Params>() {
    override suspend fun run(params: Params): Either<Failure, WeatherRecord> = repository.weather(params.queries)
    data class Params(val queries: Map<String, String>)
}