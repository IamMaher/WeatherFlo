package com.assessment.weatherflo.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assessment.weatherflo.core.exception.Failure
import com.assessment.weatherflo.data.remote.WeatherApi.Companion.API_KEY
import com.assessment.weatherflo.domain.location.LocationTracker
import com.assessment.weatherflo.domain.usecase.WeatherUseCase
import com.assessment.weatherflo.domain.weather.entity.WeatherRecord
import com.assessment.weatherflo.presentation.weather.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationTracker: LocationTracker,
    private val weatherUseCase: WeatherUseCase
) : ViewModel() {

    val shownSplash = mutableStateOf(SplashState.Shown)

    var state by mutableStateOf(WeatherState())
        private set

    fun getCurrentLocation() {
        viewModelScope.launch {
            triggerLoading()
            locationTracker.getCurrentLocation()?.let { location ->
                weatherUseCase(
                    this,
                    WeatherUseCase.Params(
                        mapOf(
                            "lat" to location.latitude.toString(),
                            "lon" to location.longitude.toString(),
                            "units" to "metric",
                            "appid" to API_KEY
                        )
                    )
                ) {
                    it.fold(
                        ::handleData,
                        ::handleFailure,
                    )
                }
            }
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
            weatherInfo = null,
            isLoading = false,
            error = failure
        )
    }

    private fun handleData(data: WeatherRecord?) {
        state = state.copy(
            weatherInfo = data,
            isLoading = false,
            error = null
        )
    }

    fun permissionIsNotGranted() {}
}
