package com.assessment.weatherflo.data.remote

import com.assessment.weatherflo.data.remote.dto.WeatherDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WeatherApi {
    @GET("weather")
    fun getWeather(@QueryMap params: Map<String, String>): Call<WeatherDto>

    companion object {
        const val API_KEY = "f5cb0b965ea1564c50c6f1b74534d823"
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }
}

//@Query("q") cityName: String,
//@Query("appid") apikey: String = API_KEY,
//@Query("units") units: String = "metric",