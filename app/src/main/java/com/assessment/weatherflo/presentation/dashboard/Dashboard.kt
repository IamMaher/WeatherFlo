package com.assessment.weatherflo.presentation.dashboard

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
import androidx.navigation.NavBackStackEntry
import com.assessment.weatherflo.presentation.forecast.Forecast
import com.assessment.weatherflo.presentation.main.MainViewModel
import com.assessment.weatherflo.presentation.weather.Weather

enum class DashboardScreen {
    Weather, Forecast
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier,
    onLocationSelectedClicked: (Int) -> Unit,
    backStackEntry: NavBackStackEntry,
    ) {
    var tabSelected by rememberSaveable { mutableStateOf(DashboardScreen.Weather) }

    Scaffold(
        modifier = modifier.statusBarsPadding(),
        topBar = { DashboardTabBar(tabSelected, onTabSelected = { tabSelected = it }) },
    ) { contentPadding ->
        DashboardContent(
            mainViewModel = mainViewModel,
            modifier = Modifier.padding(contentPadding),
            tabSelected = tabSelected,
            onLocationSelectedClicked = { onLocationSelectedClicked(it) },
            backStackEntry
        )
    }
}

private const val ANIMATED_CONTENT_ANIMATION_DURATION = 300

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DashboardContent(
    mainViewModel: MainViewModel,
    modifier: Modifier,
    tabSelected: DashboardScreen,
    onLocationSelectedClicked: (Int) -> Unit,
    backStackEntry: NavBackStackEntry
) {
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
                mainViewModel = mainViewModel,
                modifier = modifier,
                contentUpdates = ContentUpdates(
                    onLocationSelectedClicked = { onLocationSelectedClicked(it) },
                    backStackEntry = backStackEntry
                )
            )
            DashboardScreen.Forecast -> Forecast(
                mainViewModel = mainViewModel,
                modifier = modifier,
                contentUpdates = ContentUpdates(
                    onLocationSelectedClicked = { onLocationSelectedClicked(it) },
                    backStackEntry = backStackEntry,
                )
            )
        }
    }
}

data class ContentUpdates(
    val onLocationSelectedClicked: (Int) -> Unit,
    val backStackEntry: NavBackStackEntry
)