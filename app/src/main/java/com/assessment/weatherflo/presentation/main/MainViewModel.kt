package com.assessment.weatherflo.presentation.main

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assessment.weatherflo.core.exception.Failure
import com.assessment.weatherflo.core.functional.Units
import com.assessment.weatherflo.data.remote.WeatherApi
import com.assessment.weatherflo.domain.entity.forecast.ForecastData
import com.assessment.weatherflo.domain.entity.forecast.ForecastRecord
import com.assessment.weatherflo.domain.entity.weather.WeatherRecord
import com.assessment.weatherflo.domain.location.LocationTracker
import com.assessment.weatherflo.domain.usecase.ForecastUseCase
import com.assessment.weatherflo.domain.usecase.WeatherUseCase
import com.assessment.weatherflo.presentation.forecast.ForecastState
import com.assessment.weatherflo.presentation.weather.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationTracker: LocationTracker,
    private val weatherUseCase: WeatherUseCase,
    private val forecastUseCase: ForecastUseCase
) : ViewModel() {

    val shownSplash = mutableStateOf(SplashState.Shown)


    /** Weather */
    var weatherState by mutableStateOf(WeatherState())
        private set

    fun cleanWeatherEvent() {
        weatherState = weatherState.copy(isRequestPermission = false)
    }

    fun getCurrentLocationWeather() {
        viewModelScope.launch {
            triggerWeatherLoading()
            locationTracker.getCurrentLocation()?.let { location ->
                getWeather(this, location)
            }
        }
    }

    fun getWeather(coroutineScope: CoroutineScope, location: Location?) {
        val queries = mutableMapOf<String, String>()
        location?.let { latLng -> queries.putAll(mapOf("lat" to latLng.latitude.toString(), "lon" to latLng.longitude.toString())) }
        queries.putAll(mapOf("units" to weatherState.units, "appid" to WeatherApi.API_KEY))
        weatherUseCase(
            coroutineScope,
            WeatherUseCase.Params(queries)
        ) {
            it.fold(
                ::handleWeather,
                ::handleWeatherFailure,
            )
        }
    }

    fun toggleUnits() {
        when (weatherState.units) {
            Units.Metric.value -> weatherState = weatherState.copy(units = Units.Imperial.value)
            Units.Imperial.value -> weatherState = weatherState.copy(units = Units.Metric.value)
        }

        getWeather(viewModelScope, Location("").apply {
            latitude = weatherState.data!!.coord.lat
            longitude = weatherState.data!!.coord.lon
        })
    }

    private fun handleWeather(data: WeatherRecord?) {
        weatherState = weatherState.copy(
            data = data,
            isLoading = false,
            error = null
        )
    }

    private fun triggerWeatherLoading() {
        weatherState = weatherState.copy(
            isLoading = true,
            error = null
        )
    }

    private fun handleWeatherFailure(failure: Failure?) {
        weatherState = weatherState.copy(
            data = null,
            isLoading = false,
            error = failure
        )
    }


    /** Forecast */
    var forecastState by mutableStateOf(ForecastState())
        private set

    fun cleanForecastEvent() {
        forecastState = forecastState.copy(isRequestPermission = false)
    }

    fun getCurrentLocationForecast() {
        viewModelScope.launch {
            triggerForecastLoading()
            locationTracker.getCurrentLocation()?.let { location ->
                getForecast(this, location)
            }
        }
    }

    fun getForecast(coroutineScope: CoroutineScope, location: Location?) {
        val queries = mutableMapOf<String, String>()
        location?.let { latLng -> queries.putAll(mapOf("lat" to latLng.latitude.toString(), "lon" to latLng.longitude.toString())) }
        queries.putAll(mapOf("units" to "metric", "appid" to WeatherApi.API_KEY))
        forecastUseCase(coroutineScope, ForecastUseCase.Params(queries)) {
            it.fold(
                ::handleForecast,
                ::handleForecastFailure,
            )
        }
    }

    private fun handleForecast(data: ForecastRecord?) {
        forecastState = forecastState.copy(
            data = data,
            isLoading = false,
            error = null
        )
    }

    private fun triggerForecastLoading() {
        forecastState = forecastState.copy(
            isLoading = true,
            error = null
        )
    }

    private fun handleForecastFailure(failure: Failure?) {
        forecastState = forecastState.copy(
            data = null,
            isLoading = false,
            error = failure
        )
    }

    fun onClickExpandedItem(item: ForecastData) {
        viewModelScope.launch {
            forecastState.data?.let { info ->
                val forecastList = info.weatherList.toMutableList()

                if (forecastList.isNotEmpty()) {
                    val indexItem = forecastList.indexOfFirst {
                        item.time == it.time
                    }

                    if (indexItem != -1) {
                        forecastList[indexItem] = forecastList[indexItem].copy(
                            isExpanded = !forecastList[indexItem].isExpanded,
                        )
                        forecastState = forecastState.copy(data = ForecastRecord(info.city, forecastList))
                    }
                }
            }
        }
    }

    fun resetWeatherPermission() {
        weatherState = weatherState.copy(isRequestPermission = true)
    }

    fun resetForecastPermission() {
        forecastState = forecastState.copy(isRequestPermission = true)
    }

    init {
        resetWeatherPermission()
        resetForecastPermission()
    }
}
