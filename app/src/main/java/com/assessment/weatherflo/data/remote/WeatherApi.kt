package com.assessment.weatherflo.data.remote

import com.assessment.weatherflo.data.remote.dto.cities.CityDto
import com.assessment.weatherflo.data.remote.dto.cities.ZipDto
import com.assessment.weatherflo.data.remote.dto.forecast.ForecastDto
import com.assessment.weatherflo.data.remote.dto.weather.WeatherDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface WeatherApi {
    @GET("data/2.5/weather")
    fun getWeather(@QueryMap queries: Map<String, String>): Call<WeatherDto>

    @GET("data/2.5/forecast")
    fun getForecast(@QueryMap queries: Map<String, String>): Call<ForecastDto>

    @GET("geo/1.0/{type}")
    fun getCities(
        @Path("type") type: String,
        @QueryMap queries: Map<String, String>
    ): Call<List<CityDto>>

    @GET("geo/1.0/zip")
    fun getCitiesZipCode(@QueryMap queries: Map<String, String>): Call<ZipDto>

    companion object {
        const val API_KEY = "f5cb0b965ea1564c50c6f1b74534d823"
        const val BASE_URL = "https://api.openweathermap.org/"
    }
}