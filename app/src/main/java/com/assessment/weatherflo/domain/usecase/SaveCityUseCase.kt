package com.assessment.weatherflo.domain.usecase

import com.assessment.weatherflo.core.exception.Failure
import com.assessment.weatherflo.core.functional.Either
import com.assessment.weatherflo.core.interactor.UseCase
import com.assessment.weatherflo.data.local.WeatherDatabase
import com.assessment.weatherflo.domain.entity.cities.CityEntity
import javax.inject.Inject

class SaveCityUseCase @Inject constructor(private val database: WeatherDatabase) : UseCase<Unit, SaveCityUseCase.Params>() {
    override suspend fun run(params: Params): Either<Failure, Unit> = try {
        database.cityDao.insertCity(params.city)
        Either.Success(Unit)
    } catch (exception: Exception) {
        Either.Error(Failure.ServerError)
    }

    data class Params(val city: CityEntity)
}