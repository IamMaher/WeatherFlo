package com.assessment.weatherflo.presentation.weather

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assessment.weatherflo.core.exception.Failure
import com.assessment.weatherflo.data.remote.WeatherApi.Companion.API_KEY
import com.assessment.weatherflo.domain.entity.weather.WeatherRecord
import com.assessment.weatherflo.domain.location.LocationTracker
import com.assessment.weatherflo.domain.usecase.WeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val locationTracker: LocationTracker,
    private val weatherUseCase: WeatherUseCase
) : ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    fun getCurrentLocationWeather() {
        viewModelScope.launch {
            triggerLoading()
            locationTracker.getCurrentLocation()?.let { location ->
                getWeather(this, location)
            }
        }
    }

    fun getWeather(coroutineScope: CoroutineScope, location: Location?) {
        val queries = mutableMapOf<String, String>()
        location?.let { latLng -> queries.putAll(mapOf("lat" to latLng.latitude.toString(), "lon" to latLng.longitude.toString())) }
        queries.putAll(mapOf("units" to "metric", "appid" to API_KEY))
        weatherUseCase(
            coroutineScope,
            WeatherUseCase.Params(queries)
        ) {
            it.fold(
                ::handleWeather,
                ::handleFailure,
            )
        }
    }


    private fun triggerLoading() {
        state = state.copy(
            isLoading = true,
            error = null
        )
    }

    private fun handleFailure(failure: Failure?) {
        state = state.copy(
            data = null,
            isLoading = false,
            error = failure
        )
    }

    private fun handleWeather(data: WeatherRecord?) {
        state = state.copy(
            data = data,
            isLoading = false,
            error = null
        )
    }

    fun permissionIsNotGranted() {}
}
