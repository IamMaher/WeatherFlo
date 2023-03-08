package com.assessment.weatherflo.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.animation.core.Spring.StiffnessLow
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.assessment.weatherflo.presentation.dashboard.Dashboard
import com.assessment.weatherflo.presentation.landing.LandingScreen
import com.assessment.weatherflo.ui.theme.WeatherFloTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            WeatherFloTheme {
                rememberSystemUiController().setSystemBarsColor(color = Color.Transparent, darkIcons = !isSystemInDarkTheme())
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.Dashboard.route) {
                    composable(Routes.Dashboard.route) {
                        val mainViewModel = hiltViewModel<MainViewModel>()
                        MainScreen(
                            onLocationSelectionClicked = {
                                // todo navigate to select location screen
                            },
                            mainViewModel = mainViewModel
                        )
                    }
                }
            }
        }
    }
}

sealed class Routes(val route: String) {
    object Dashboard : Routes("Dashboard")
}

@Composable
fun MainScreen(
    onLocationSelectionClicked: () -> Unit,
    mainViewModel: MainViewModel
) {
    Surface(
        modifier = Modifier.windowInsetsPadding(
            WindowInsets.navigationBars.only(WindowInsetsSides.Start + WindowInsetsSides.End)
        ),
    ) {
        val transitionState = remember { MutableTransitionState(mainViewModel.shownSplash.value) }
        val transition = updateTransition(transitionState, label = "splashTransition")
        val contentAlpha by transition.animateFloat(
            transitionSpec = { tween(durationMillis = 300) }, label = "contentAlpha"
        ) {
            if (it == SplashState.Shown) 0f else 1f
        }
        val contentTopPadding by transition.animateDp(
            transitionSpec = { spring(stiffness = StiffnessLow) }, label = "contentTopPadding"
        ) {
            if (it == SplashState.Shown) 100.dp else 0.dp
        }

        Box {
            LandingScreen {
                transitionState.targetState = SplashState.Completed
                mainViewModel.shownSplash.value = SplashState.Completed
            }

            MainContent(
                modifier = Modifier.alpha(contentAlpha),
                topPadding = contentTopPadding,
                onLocationSelectedClicked = onLocationSelectionClicked
            )
        }
    }
}

@Composable
private fun MainContent(
    modifier: Modifier = Modifier,
    topPadding: Dp = 0.dp,
    onLocationSelectedClicked: () -> Unit,
) {

    Column(modifier = modifier) {
        Spacer(Modifier.padding(top = topPadding))
        Dashboard(
            onLocationClicked = onLocationSelectedClicked,
            modifier = modifier,
        )
    }
}

enum class SplashState { Shown, Completed }
