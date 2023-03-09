package com.assessment.weatherflo.data.remote

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherFloService @Inject constructor(retrofit: Retrofit) : WeatherApi {
    private val api by lazy { retrofit.create(WeatherApi::class.java) }
    override fun getWeather(queries: Map<String, String>) = api.getWeather(queries)
    override fun getForecast(queries: Map<String, String>) = api.getForecast(queries)
    override fun getCities(type:String, queries: Map<String, String>) = api.getCities(type,queries)
    override fun getCitiesZipCode(queries: Map<String, String>) = api.getCitiesZipCode(queries)
}