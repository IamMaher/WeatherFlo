package com.assessment.weatherflo.domain.usecase

import com.assessment.weatherflo.core.exception.Failure
import com.assessment.weatherflo.core.functional.Either
import com.assessment.weatherflo.core.interactor.UseCase
import com.assessment.weatherflo.data.local.WeatherDatabase
import com.assessment.weatherflo.domain.entity.cities.CityEntity
import javax.inject.Inject

class GetCityHistoryUseCase @Inject constructor(private val database: WeatherDatabase) : UseCase<List<CityEntity>, UseCase.None>() {
    private val threshold = System.currentTimeMillis() - 5 * 60 * 1000
    override suspend fun run(params: None): Either<Failure, List<CityEntity>> = try {
        Either.Success(database.cityDao.getAllCities(threshold))
    } catch (exception: Exception) {
        Either.Error(Failure.ServerError)
    }
}