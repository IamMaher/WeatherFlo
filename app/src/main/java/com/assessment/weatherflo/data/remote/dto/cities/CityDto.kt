package com.assessment.weatherflo.data.remote.dto.cities


import com.assessment.weatherflo.domain.entity.cities.CityEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CityDto(
    @field:Json(name = "country") val country: String ="",
    @field:Json(name = "lat") val lat: Double = -1.0,
    @field:Json(name = "local_names") val localNames: LocalNames = LocalNames(),
    @field:Json(name = "lon") val lon: Double= -1.0,
    @field:Json(name = "name") val name: String = "",
    @field:Json(name = "state") val state: String = ""
)

fun CityDto.toCity(): CityEntity {
    return CityEntity(
        name = name,
        country = country,
        state = state,
        lat = lat,
        lon = lon,
    )
}