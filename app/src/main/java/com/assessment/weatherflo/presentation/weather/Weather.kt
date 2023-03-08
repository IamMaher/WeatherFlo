package com.assessment.weatherflo.presentation.weather

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.assessment.weatherflo.R
import com.assessment.weatherflo.domain.entity.weather.WeatherRecord
import com.assessment.weatherflo.presentation.components.FullScreenLoading
import com.assessment.weatherflo.presentation.dashboard.ContentUpdates
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.Dispatchers


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun Weather(modifier: Modifier, viewModel: WeatherViewModel = hiltViewModel(), contentUpdates: ContentUpdates) {
    val state = viewModel.state
    val locationPermissionState = rememberMultiplePermissionsState(
        listOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
    ) { permissions ->
        when {
            permissions.all { it.value } -> viewModel.getCurrentLocationWeather()
            else -> viewModel.permissionIsNotGranted()
        }
    }

    LaunchedEffect(Unit, Dispatchers.Default) {
        locationPermissionState.launchMultiplePermissionRequest()
    }

    Column(modifier = modifier) {
        Card(
            onClick = contentUpdates.onLocationSelectionClicked,
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
        state.data?.let { data -> WeatherContent(data) }
    }
}

@Composable
fun WeatherContent(currentWeather: WeatherRecord) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        CurrentWeather(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            currentWeather = currentWeather,
        )
    }
}