package com.assessment.weatherflo.presentation.dashboard

import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DashboardTabBar(
    tabSelected: DashboardScreen,
    onTabSelected: (DashboardScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    TabBar(
        modifier = modifier
            .wrapContentWidth()
            .sizeIn(maxWidth = 500.dp)
    ) { tabBarModifier ->
        Tabs(
            modifier = tabBarModifier,
            titles = DashboardScreen.values().map { it.name },
            tabSelected = tabSelected,
            onTabSelected = { newTab -> onTabSelected(DashboardScreen.values()[newTab.ordinal]) }
        )
    }
}