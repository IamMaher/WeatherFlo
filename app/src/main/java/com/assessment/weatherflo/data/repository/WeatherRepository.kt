package com.assessment.weatherflo.data.repository

import com.assessment.weatherflo.core.exception.Failure
import com.assessment.weatherflo.core.exception.Failure.NetworkConnection
import com.assessment.weatherflo.core.exception.Failure.ServerError
import com.assessment.weatherflo.core.functional.Either
import com.assessment.weatherflo.core.functional.Either.Error
import com.assessment.weatherflo.core.functional.Either.Success
import com.assessment.weatherflo.core.platform.NetworkHandler
import com.assessment.weatherflo.data.remote.WeatherService
import com.assessment.weatherflo.data.remote.dto.WeatherDto
import com.assessment.weatherflo.data.remote.dto.toWeatherRecord
import com.assessment.weatherflo.domain.weather.entity.WeatherRecord
import retrofit2.Call
import javax.inject.Inject

interface WeatherRepository {
    fun weather(queries: Map<String, String>): Either<Failure, WeatherRecord>
    class Network
    @Inject constructor(private val networkHandler: NetworkHandler, private val service: WeatherService) : WeatherRepository {

        override fun weather(queries: Map<String, String>): Either<Failure, WeatherRecord> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.getWeather(queries), { it.toWeatherRecord() }, WeatherDto.empty)
                false -> Error(NetworkConnection)
            }
        }

        private fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
            return try {
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> Success(transform((response.body() ?: default)))
                    else -> Error(ServerError)
                }
            } catch (exception: Throwable) {
                Error(ServerError)
            }
        }
    }
}