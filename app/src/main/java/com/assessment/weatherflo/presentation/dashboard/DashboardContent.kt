package com.assessment.weatherflo.presentation.dashboard

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.assessment.weatherflo.domain.weather.entity.WeatherRecord
import com.assessment.weatherflo.presentation.MainViewModel

@Composable
fun WeatherContent(modifier: Modifier, currentWeather: WeatherRecord) {
    Column(
        modifier = modifier
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


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardContent(
    widthSize: WindowWidthSizeClass,
    onLocationSelectionClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {

    var tabSelected by rememberSaveable { mutableStateOf(DashboardScreen.Weather) }

    Scaffold(
        modifier = modifier,
        topBar = { DashboardTabBar(tabSelected, onTabSelected = { tabSelected = it }) },
        content = {}
    )
}