package com.assessment.weatherflo.presentation.forecast

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assessment.weatherflo.core.exception.Failure
import com.assessment.weatherflo.data.remote.WeatherApi.Companion.API_KEY
import com.assessment.weatherflo.domain.entity.forecast.ForecastData
import com.assessment.weatherflo.domain.entity.forecast.ForecastRecord
import com.assessment.weatherflo.domain.location.LocationTracker
import com.assessment.weatherflo.domain.usecase.ForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val locationTracker: LocationTracker,
    private val forecastUseCase: ForecastUseCase
) : ViewModel() {

    var state by mutableStateOf(ForecastState())
        private set

    fun getCurrentLocationForecast() {
        viewModelScope.launch {
            triggerLoading()
            locationTracker.getCurrentLocation()?.let { location ->
                getForecast(this, location)
            }
        }
    }

    fun getForecast(coroutineScope: CoroutineScope, location: Location?) {
        val queries = mutableMapOf<String, String>()
        location?.let { latLng -> queries.putAll(mapOf("lat" to latLng.latitude.toString(), "lon" to latLng.longitude.toString())) }
        queries.putAll(mapOf("units" to "metric", "appid" to API_KEY))
        forecastUseCase(coroutineScope, ForecastUseCase.Params(queries)) {
            it.fold(
                ::handleData,
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

    private fun handleData(data: ForecastRecord?) {
        state = state.copy(
            data = data,
            isLoading = false,
            error = null
        )
    }

    fun onClickExpandedItem(item: ForecastData) {
        viewModelScope.launch {
            state.data?.let { info ->
                val forecastList = info.weatherList.toMutableList()

                if (forecastList.isNotEmpty()) {
                    val indexItem = forecastList.indexOfFirst {
                        item.time == it.time
                    }

                    if (indexItem != -1) {
                        forecastList[indexItem] = forecastList[indexItem].copy(
                            isExpanded = !forecastList[indexItem].isExpanded,
                        )
                        state = state.copy(data = ForecastRecord(info.city, forecastList))
                    }
                }
            }
        }
    }

    fun permissionIsNotGranted() {}
}
