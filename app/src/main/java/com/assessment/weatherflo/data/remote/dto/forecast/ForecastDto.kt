package com.assessment.weatherflo.data.remote.dto.forecast


import com.assessment.weatherflo.domain.entity.forecast.ForecastRecord
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastDto(
    @field:Json(name = "city") val city: City = City(),
    @field:Json(name = "cnt") val cnt: Int = -1,
    @field:Json(name = "cod") val cod: String = "",
    @field:Json(name = "list") val list: List<WeatherList> = emptyList(),
    @field:Json(name = "message") val message: Int = -1
) {
    companion object {
        val empty = ForecastDto()
    }
}

fun ForecastDto.toForecastRecord(): ForecastRecord {
    return ForecastRecord(
        city = city.name,
        weatherList = list.map { it.toForecastList() }
    )
}