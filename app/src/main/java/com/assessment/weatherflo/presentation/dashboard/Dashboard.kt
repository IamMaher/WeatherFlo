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
import com.assessment.weatherflo.presentation.forecast.Forecast
import com.assessment.weatherflo.presentation.weather.Weather

enum class DashboardScreen {
    Weather, Forecast
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(
    modifier: Modifier = Modifier,
    onLocationClicked: () -> Unit
) {
    var tabSelected by rememberSaveable { mutableStateOf(DashboardScreen.Weather) }

    Scaffold(
        modifier = modifier.statusBarsPadding(),
        topBar = { DashboardTabBar(tabSelected, onTabSelected = { tabSelected = it }) },
    ) { contentPadding ->
        DashboardContent(
            modifier = Modifier.padding(contentPadding),
            tabSelected = tabSelected,
            onLocationSelectionClicked = onLocationClicked,
        )
    }
}

private const val ANIMATED_CONTENT_ANIMATION_DURATION = 300

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DashboardContent(
    modifier: Modifier,
    tabSelected: DashboardScreen,
    onLocationSelectionClicked: () -> Unit
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
                modifier,
                contentUpdates = ContentUpdates(
                    onLocationSelectionClicked = onLocationSelectionClicked,
                )
            )
            DashboardScreen.Forecast -> Forecast(
                modifier,
                contentUpdates = ContentUpdates(
                    onLocationSelectionClicked = onLocationSelectionClicked,
                )
            )
        }
    }
}

data class ContentUpdates(
    val onLocationSelectionClicked: () -> Unit
)