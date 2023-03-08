package com.assessment.weatherflo.domain.usecase

import com.assessment.weatherflo.core.exception.Failure
import com.assessment.weatherflo.core.functional.Either
import com.assessment.weatherflo.core.interactor.UseCase
import com.assessment.weatherflo.data.repository.Repository
import com.assessment.weatherflo.domain.entity.forecast.ForecastRecord
import javax.inject.Inject

class ForecastUseCase @Inject constructor(private val repository: Repository) :
    UseCase<ForecastRecord, ForecastUseCase.Params>() {
    override suspend fun run(params: Params): Either<Failure, ForecastRecord> = repository.forecast(params.queries)
    data class Params(val queries: Map<String, String>)
}