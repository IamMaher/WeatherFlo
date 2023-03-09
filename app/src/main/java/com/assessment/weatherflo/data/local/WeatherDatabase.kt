package com.assessment.weatherflo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.assessment.weatherflo.domain.entity.cities.CityEntity

@Database(
    entities = [CityEntity::class],
    version = 1
)
abstract class WeatherDatabase: RoomDatabase() {
    abstract val cityDao: CityDao
}