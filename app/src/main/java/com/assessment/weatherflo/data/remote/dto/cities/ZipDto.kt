package com.assessment.weatherflo.data.remote.dto.cities

import com.assessment.weatherflo.domain.entity.cities.CityEntity
import com.squareup.moshi.Json

data class ZipDto(
    @field:Json(name = "country") val country: String = "",
    @field:Json(name = "lat") val lat: Double = -1.0,
    @field:Json(name = "lon") val lon: Double = -1.0,
    @field:Json(name = "name") val name: String = "",
    @field:Json(name = "zip") val zip: String = ""
){
    companion object {
        val empty = ZipDto()
    }
}

fun ZipDto.toCity(): CityEntity {
    return CityEntity(
        name = name,
        country = country,
        lat = lat,
        lon = lon,
    )
}