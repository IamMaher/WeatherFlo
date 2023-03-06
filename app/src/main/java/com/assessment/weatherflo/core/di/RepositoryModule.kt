package com.assessment.weatherflo.core.di

import com.assessment.weatherflo.data.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideWeatherRepo(dataSource: WeatherRepository.Network): WeatherRepository = dataSource
}