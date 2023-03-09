package com.assessment.weatherflo.domain.entity.cities

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(primaryKeys = ["name", "country"])
data class CityEntity(
    val name: String = "",
    val country: String = "",
    val state: String? = "",
    val lat: Double = -1.0,
    val lon: Double = -1.0,
    var timestamp: Long = 0,
) : Parcelable
