package com.assessment.weatherflo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.assessment.weatherflo.domain.entity.cities.CityEntity

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCities(citiesEntities: List<CityEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(cityEntities: CityEntity)

    @Query("SELECT * FROM cityentity WHERE timestamp >= :threshold")
    fun getAllCities(threshold: Long): List<CityEntity>

    @Query("DELETE FROM cityentity")
    fun clearCityListing()
}