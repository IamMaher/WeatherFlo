package com.assessment.weatherflo.domain.usecase

import com.assessment.weatherflo.core.exception.Failure
import com.assessment.weatherflo.core.functional.Either
import com.assessment.weatherflo.core.interactor.UseCase
import com.assessment.weatherflo.data.local.WeatherDatabase
import com.assessment.weatherflo.domain.entity.cities.CityEntity
import javax.inject.Inject

class ClearCitiesUseCase @Inject constructor(private val database: WeatherDatabase) : UseCase<List<CityEntity>, UseCase.None>() {
    override suspend fun run(params: None): Either<Failure, List<CityEntity>> = try {
        database.cityDao.clearCityListing()
        Either.Success(emptyList())
    } catch (exception: Exception) {
        Either.Error(Failure.ServerError)
    }
}