package com.assessment.weatherflo.presentation.weather

import android.Manifest
import android.location.Location
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.sharp.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.assessment.weatherflo.R
import com.assessment.weatherflo.core.functional.Constants
import com.assessment.weatherflo.domain.entity.cities.CityEntity
import com.assessment.weatherflo.domain.entity.weather.WeatherRecord
import com.assessment.weatherflo.presentation.components.FullScreenLoading
import com.assessment.weatherflo.presentation.dashboard.ContentUpdates
import com.assessment.weatherflo.presentation.dashboard.DashboardScreen
import com.assessment.weatherflo.presentation.main.MainViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState


@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Weather(mainViewModel: MainViewModel, modifier: Modifier, contentUpdates: ContentUpdates) {
    val state = mainViewModel.weatherState
    val locationPermissionState = rememberMultiplePermissionsState(
        listOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
    ) { permissions ->
        if (permissions.all { it.value }) mainViewModel.getCurrentLocationWeather()
    }

    LaunchedEffect(state) {
        val requestPermission = state.isRequestPermission

        when {
            requestPermission -> {
                when {
                    locationPermissionState.allPermissionsGranted -> mainViewModel.getCurrentLocationWeather()
                    else -> locationPermissionState.launchMultiplePermissionRequest()
                }
            }

            else -> return@LaunchedEffect
        }
        mainViewModel.cleanWeatherEvent()
    }

    LaunchedEffect(Unit) {
        if (contentUpdates.backStackEntry.savedStateHandle.contains(Constants.Key.WEATHER_LOCATION)) {
            val keyData = contentUpdates.backStackEntry.savedStateHandle.get<Boolean>(Constants.Key.WEATHER_LOCATION) ?: false
            if (keyData) mainViewModel.resetWeatherPermission()
        }
    }

    LaunchedEffect(Unit) {
        if (contentUpdates.backStackEntry.savedStateHandle.contains(Constants.Key.WEATHER_SELECT)) {
            val cityEntity = contentUpdates.backStackEntry.savedStateHandle.get<CityEntity>(Constants.Key.WEATHER_SELECT) ?: CityEntity()
            mainViewModel.getWeather(mainViewModel.viewModelScope, Location("").apply {
                latitude = cityEntity.lat
                longitude = cityEntity.lon
            })
        }
    }

    Column(modifier = modifier) {
        Card(
            onClick = { contentUpdates.onLocationSelectedClicked(DashboardScreen.Weather.ordinal) },
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Icon(
                    imageVector = Icons.Sharp.LocationOn,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 5.dp),
                )

                Text(
                    text = state.data?.city ?: stringResource(id = R.string.unknown_address),
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f),
                )

                Button(onClick = { mainViewModel.toggleUnits() }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.Refresh,
                            contentDescription = null,
                        )
                        Text(text = state.units)
                    }
                }
            }
        }

        if (state.isLoading) FullScreenLoading()
        state.data?.let { data -> WeatherContent(state, data) }
    }
}

@Composable
fun WeatherContent(state: WeatherState, currentWeather: WeatherRecord) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        CurrentWeather(
            state,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            currentWeather = currentWeather,
        )
    }
}