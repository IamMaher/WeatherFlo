package com.assessment.weatherflo.data.remote.dto.weather


import com.assessment.weatherflo.core.extenstion.toDateTime
import com.assessment.weatherflo.core.functional.Constants
import com.assessment.weatherflo.domain.entity.WeatherType
import com.assessment.weatherflo.domain.entity.weather.WeatherRecord
import com.squareup.moshi.Json

data class WeatherDto(
    @field:Json(name = "base") val base: String = "",
    @field:Json(name = "clouds") val clouds: Clouds,
    @field:Json(name = "cod") val cod: Int = -1,
    @field:Json(name = "coord") val coord: Coord,
    @field:Json(name = "dt") val dt: Long = -1,
    @field:Json(name = "id") val id: Int = -1,
    @field:Json(name = "main") val main: Main,
    @field:Json(name = "name") val name: String = "",
    @field:Json(name = "snow") val snow: Snow,
    @field:Json(name = "sys") val sys: Sys,
    @field:Json(name = "timezone") val timezone: Int = -1,
    @field:Json(name = "visibility") val visibility: Int = -1,
    @field:Json(name = "weather") val weather: List<Weather> = emptyList(),
    @field:Json(name = "wind") val wind: Wind
) {
    companion object {
        val empty = WeatherDto(clouds = Clouds(), coord = Coord(), main = Main(), snow = Snow(), sys = Sys(), wind = Wind())
    }
}

fun WeatherDto.toWeatherRecord(): WeatherRecord {
    return WeatherRecord(
        time = dt.times(1000).toDateTime(Constants.DateFormat.EE_MM_dd),
        tempMax = main.tempMax,
        tempMin = main.tempMin,
        temp = main.temp.toString(),
        sunrise = sys.sunrise.toDateTime(Constants.DateFormat.HH_mm, timezone),
        pressure = main.pressure,
        windSpeed = wind.speed,
        humidity = main.humidity,
        city = name,
        weatherType = WeatherType.fromWMO(weather.first().icon.substring(0, 2), weather.first().description)
    )
}