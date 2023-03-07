package com.assessment.weatherflo.presentation.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.assessment.weatherflo.presentation.MainViewModel

enum class DashboardScreen {
    Weather, Forecast
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(
    widthSize: WindowWidthSizeClass,
    onLocationClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    Scaffold(
        modifier = Modifier.statusBarsPadding(),
    ) { contentPadding ->
        DashboardContent(
            widthSize = widthSize,
            onLocationSelectionClicked = onLocationClicked,
            modifier = modifier.padding(contentPadding),
            viewModel = viewModel
        )
    }
}