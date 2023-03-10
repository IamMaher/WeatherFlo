package com.assessment.weatherflo.presentation.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.assessment.weatherflo.R
import com.assessment.weatherflo.core.functional.Units
import com.assessment.weatherflo.domain.entity.weather.WeatherRecord

@Composable
fun CurrentWeather(
    state: WeatherState,
    modifier: Modifier,
    currentWeather: WeatherRecord
) {
    Row(modifier = modifier) {
        Image(
            painter = painterResource(id = currentWeather.weatherType.iconRes),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 150.dp)
                .weight(1f),
            alignment = Alignment.CenterEnd,
            contentScale = ContentScale.Fit,
        )

        Column(
            modifier = Modifier
                .padding(end = 15.dp, bottom = 150.dp)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(
                    id = if (state.units == Units.Metric.value) R.string.celsius_high_low else R.string.fahrenheit_high_low,
                    currentWeather.tempMax,
                    currentWeather.tempMin,
                ),
            )

            Degrees(
                state,
                currentWeather = currentWeather.weatherType.desc,
                currentTemp = currentWeather.temp,
            )

            DetailWeather(
                iconId = R.drawable.ic_sunrise,
                title = stringResource(id = R.string.sunrise),
                description = currentWeather.sunrise,
            )

            DetailWeather(
                iconId = R.drawable.ic_wind,
                title = stringResource(id = R.string.wind),
                description = stringResource(
                    if (state.units == Units.Metric.value) R.string.meter_per_second else R.string.miles_per_hour,
                    currentWeather.windSpeed
                ),
            )

            DetailWeather(
                iconId = R.drawable.ic_humidity,
                title = stringResource(id = R.string.humidity),
                description = stringResource(id = R.string.humidity_per_cent, currentWeather.humidity),
            )
        }
    }
}