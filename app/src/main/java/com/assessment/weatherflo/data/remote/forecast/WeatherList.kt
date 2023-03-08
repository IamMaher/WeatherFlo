package com.assessment.weatherflo.data.remote.forecast


import com.assessment.weatherflo.domain.entity.WeatherType
import com.assessment.weatherflo.domain.entity.forecast.ForecastData
import com.squareup.moshi.Json

data class WeatherList(
    @field:Json(name = "clouds") val clouds: Clouds,
    @field:Json(name = "dt") val dt: Long = -1,
    @field:Json(name = "dt_txt") val dtTxt: String = "",
    @field:Json(name = "main") val main: Main,
    @field:Json(name = "pop") val pop: Double = -1.0,
    @field:Json(name = "rain") val rain: Rain,
    @field:Json(name = "sys") val sys: Sys,
    @field:Json(name = "visibility") val visibility: Int = -1,
    @field:Json(name = "weather") val weather: List<Weather>,
    @field:Json(name = "wind") val wind: Wind
)

fun WeatherList.toForecastList(): ForecastData {
    return ForecastData(
        dt,
        tempMax = main.tempMax,
        tempMin = main.tempMin,
        temp = main.temp.toString(),
        pressure = main.pressure,
        windSpeed = wind.speed,
        humidity = main.humidity,
        weatherType = WeatherType.fromWMO(weather.first().icon.substring(0, 2), weather.first().description)
    )
}