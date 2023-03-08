package com.assessment.weatherflo.data.remote

import com.assessment.weatherflo.data.remote.dto.weather.WeatherDto
import com.assessment.weatherflo.data.remote.forecast.ForecastDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WeatherApi {
    @GET("weather")
    fun getWeather(@QueryMap params: Map<String, String>): Call<WeatherDto>

    @GET("forecast")
    fun getForecast(@QueryMap params: Map<String, String>): Call<ForecastDto>

    companion object {
        const val API_KEY = "f5cb0b965ea1564c50c6f1b74534d823"
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }
}