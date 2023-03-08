package com.assessment.weatherflo.presentation.dashboard

import android.Manifest
import androidx.compose.animation.*
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.assessment.weatherflo.presentation.MainViewModel
import com.assessment.weatherflo.presentation.weather.Weather
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

enum class DashboardScreen {
    Weather, Forecast
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(
    modifier: Modifier = Modifier,
    onLocationClicked: () -> Unit,
    viewModel: MainViewModel
) {
    var tabSelected by rememberSaveable { mutableStateOf(DashboardScreen.Weather) }

    Scaffold(
        modifier = modifier.statusBarsPadding(),
        topBar = { DashboardTabBar(tabSelected, onTabSelected = { tabSelected = it }) },
    ) { contentPadding ->
        DashboardContent(
            Modifier.padding(contentPadding),
            onLocationSelectionClicked = onLocationClicked,
            viewModel = viewModel
        )
    }
}

private const val ANIMATED_CONTENT_ANIMATION_DURATION = 300

@OptIn(ExperimentalAnimationApi::class, ExperimentalPermissionsApi::class)
@Composable
fun DashboardContent(
    modifier: Modifier,
    onLocationSelectionClicked: () -> Unit,
    viewModel: MainViewModel
) {
    val state = viewModel.state
    val tabSelected by rememberSaveable { mutableStateOf(DashboardScreen.Weather) }
    val locationPermissionState = rememberMultiplePermissionsState(
        listOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
    ) { permissions ->
        when {
            permissions.all { it.value } -> viewModel.getCurrentLocation()
            else -> viewModel.permissionIsNotGranted()
        }
    }

    LaunchedEffect(Unit) {
        locationPermissionState.launchMultiplePermissionRequest()
    }

    AnimatedContent(
        targetState = tabSelected,
        transitionSpec = {
            fadeIn(
                animationSpec = tween(ANIMATED_CONTENT_ANIMATION_DURATION, easing = EaseIn)
            ).with(
                fadeOut(
                    animationSpec = tween(ANIMATED_CONTENT_ANIMATION_DURATION, easing = EaseOut)
                )
            ).using(
                SizeTransform(
                    sizeAnimationSpec = { _, _ ->
                        tween(ANIMATED_CONTENT_ANIMATION_DURATION, easing = EaseInOut)
                    }
                )
            )
        },
    ) { targetState ->
        when (targetState) {
            DashboardScreen.Weather -> Weather(
                modifier,
                currentWeather = state.weatherInfo,
                contentUpdates = ContentUpdates(
                    onLocationSelectionClicked = onLocationSelectionClicked,
                )
            )
            DashboardScreen.Forecast -> {}
        }
    }
}

data class ContentUpdates(
    val onLocationSelectionClicked: () -> Unit
)