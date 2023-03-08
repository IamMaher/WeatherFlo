package com.assessment.weatherflo.presentation.weather

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.assessment.weatherflo.R
import com.assessment.weatherflo.domain.weather.entity.WeatherRecord
import com.assessment.weatherflo.presentation.dashboard.ContentUpdates


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Weather(modifier: Modifier, currentWeather: WeatherRecord?, contentUpdates: ContentUpdates) {
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
                    text = currentWeather?.city ?: stringResource(id = R.string.unknown_address),
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }

        currentWeather?.let { data -> WeatherContent(data) }
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