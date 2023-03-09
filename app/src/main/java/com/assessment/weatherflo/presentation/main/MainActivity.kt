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
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.assessment.weatherflo.core.functional.Constants
import com.assessment.weatherflo.presentation.dashboard.Dashboard
import com.assessment.weatherflo.presentation.dashboard.DashboardScreen
import com.assessment.weatherflo.presentation.landing.LandingScreen
import com.assessment.weatherflo.presentation.search.Search
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
                    composable(Routes.Dashboard.route) { backStackEntry ->
                        val mainViewModel = hiltViewModel<MainViewModel>()
                        MainScreen(
                            onLocationSelectionClicked = { screen -> navController.navigate(Routes.Search.createRoute(screen)) },
                            mainViewModel = mainViewModel,
                            backStackEntry
                        )
                    }
                    composable(Routes.Search.route) { backStackEntry ->
                        backStackEntry.arguments?.getString("extraData")
                        Search(
                            screen = backStackEntry.arguments?.getString("extraData")?.toInt() ?: -1,
                            onBackPressed = { navController.popBackStack() },
                            viewModel = hiltViewModel(),
                            onSearchCurrentLocation = { screen ->
                                navController.previousBackStackEntry?.savedStateHandle?.set(
                                    if (screen == DashboardScreen.Weather.ordinal) Constants.Key.WEATHER_LOCATION
                                    else Constants.Key.FORECAST_LOCATION, true
                                )
                                navController.popBackStack()
                            },
                            onSelectResult = { screen, city ->
                                navController.previousBackStackEntry?.savedStateHandle?.set(
                                    if (screen == DashboardScreen.Weather.ordinal) Constants.Key.WEATHER_SELECT
                                    else Constants.Key.FORECAST_SELECT, city
                                )
                                navController.popBackStack()
                            },
                        )
                    }
                }
            }
        }
    }
}

sealed class Routes(val route: String) {
    object Dashboard : Routes("Dashboard")
    object Search : Routes("Search/{extraData}") {
        fun createRoute(extraData: Int): String {
            return "Search/$extraData"
        }
    }
}

@Composable
fun MainScreen(
    onLocationSelectionClicked: (Int) -> Unit,
    mainViewModel: MainViewModel,
    backStackEntry: NavBackStackEntry
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
                mainViewModel = mainViewModel,
                modifier = Modifier.alpha(contentAlpha),
                topPadding = contentTopPadding,
                onLocationSelectedClicked = onLocationSelectionClicked,
                backStackEntry
            )
        }
    }
}

@Composable
private fun MainContent(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier,
    topPadding: Dp = 0.dp,
    onLocationSelectedClicked: (Int) -> Unit,
    backStackEntry: NavBackStackEntry,
) {

    Column(modifier = modifier) {
        Spacer(Modifier.padding(top = topPadding))
        Dashboard(
            mainViewModel = mainViewModel,
            onLocationSelectedClicked = { onLocationSelectedClicked(it) },
            modifier = modifier,
            backStackEntry = backStackEntry
        )
    }
}

enum class SplashState { Shown, Completed }
