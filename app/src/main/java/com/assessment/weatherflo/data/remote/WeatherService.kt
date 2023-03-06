package com.assessment.weatherflo.data.remote

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherService @Inject constructor(retrofit: Retrofit) : WeatherApi {
    private val api by lazy { retrofit.create(WeatherApi::class.java) }
    override fun getWeather(params: Map<String, String>) = api.getWeather(params)
}