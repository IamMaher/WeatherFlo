package com.assessment.weatherflo.presentation.forecast

import android.Manifest
import android.location.Location
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import com.assessment.weatherflo.domain.entity.forecast.ForecastData
import com.assessment.weatherflo.presentation.components.FullScreenLoading
import com.assessment.weatherflo.presentation.dashboard.ContentUpdates
import com.assessment.weatherflo.presentation.dashboard.DashboardScreen
import com.assessment.weatherflo.presentation.main.MainViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun Forecast(mainViewModel: MainViewModel, modifier: Modifier, contentUpdates: ContentUpdates) {
    val state = mainViewModel.forecastState
    val locationPermissionState = rememberMultiplePermissionsState(
        listOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
    ) { permissions ->
        if (permissions.all { it.value }) mainViewModel.getCurrentLocationForecast()
    }


    LaunchedEffect(state) {
        val requestPermission = state.isRequestPermission

        when {
            requestPermission -> {
                when {
                    locationPermissionState.allPermissionsGranted -> mainViewModel.getCurrentLocationForecast()
                    else -> locationPermissionState.launchMultiplePermissionRequest()
                }
            }

            else -> return@LaunchedEffect
        }
        mainViewModel.cleanForecastEvent()
    }

    LaunchedEffect(Unit) {
        if (contentUpdates.backStackEntry.savedStateHandle.contains(Constants.Key.FORECAST_LOCATION)) {
            val keyData = contentUpdates.backStackEntry.savedStateHandle.get<Boolean>(Constants.Key.FORECAST_LOCATION) ?: false
            if (keyData) mainViewModel.resetForecastPermission()
        }
    }

    LaunchedEffect(Unit) {
        if (contentUpdates.backStackEntry.savedStateHandle.contains(Constants.Key.FORECAST_SELECT)) {
            val cityEntity = contentUpdates.backStackEntry.savedStateHandle.get<CityEntity>(Constants.Key.FORECAST_SELECT) ?: CityEntity()
            mainViewModel.getForecast(mainViewModel.viewModelScope, Location("").apply {
                latitude = cityEntity.lat
                longitude = cityEntity.lon
            })
        }
    }

    Column(modifier = modifier) {
        Card(
            onClick = { contentUpdates.onLocationSelectedClicked(DashboardScreen.Forecast.ordinal) },
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
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
                )
            }
        }

        if (state.isLoading) FullScreenLoading()
        state.data?.let { data ->
            ForecastContent(
                forecastData = data.weatherList,
                onClickExpandedItem = { mainViewModel.onClickExpandedItem(it) }
            )
        }
    }
}

@Composable
fun ForecastContent(
    forecastData: List<ForecastData>, onClickExpandedItem: (item: ForecastData) -> Unit = {}
) {
    ListWeatherDay(
        modifier = Modifier.fillMaxSize(),
        list = forecastData,
        onClickExpandedItem = onClickExpandedItem
    )
}

@Composable
fun ListWeatherDay(
    modifier: Modifier,
    list: List<ForecastData> = emptyList(),
    onClickExpandedItem: (item: ForecastData) -> Unit = {},
) {
    val paddingBottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = paddingBottom + 10.dp),
    ) {
        items(
            items = list,
            key = { item -> item.time },
        ) { item ->
            WeatherDayItem(
                modifier = Modifier.fillMaxWidth(),
                item = item,
                onClickExpandedItem = onClickExpandedItem,
            )
        }
    }
}