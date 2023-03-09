package com.assessment.weatherflo.domain.usecase

import com.assessment.weatherflo.core.exception.Failure
import com.assessment.weatherflo.core.functional.Either
import com.assessment.weatherflo.core.interactor.UseCase
import com.assessment.weatherflo.data.repository.Repository
import com.assessment.weatherflo.domain.entity.cities.CityEntity
import javax.inject.Inject

class CitiesUseCase @Inject constructor(private val repository: Repository) :
    UseCase<List<CityEntity>, CitiesUseCase.Params>() {
    override suspend fun run(params: Params): Either<Failure, List<CityEntity>> = repository.cities(params.type, params.queries)
    data class Params(val type: String, val queries: Map<String, String>)
}