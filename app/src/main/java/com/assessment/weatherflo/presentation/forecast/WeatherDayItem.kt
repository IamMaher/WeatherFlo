package com.assessment.weatherflo.presentation.forecast

import androidx.compose.animation.*
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.assessment.weatherflo.R
import com.assessment.weatherflo.core.extenstion.toDateTime
import com.assessment.weatherflo.core.functional.Constants
import com.assessment.weatherflo.domain.entity.forecast.ForecastData
import java.util.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WeatherDayItem(
    modifier: Modifier = Modifier,
    item: ForecastData,
    onClickExpandedItem: (item: ForecastData) -> Unit = {},
) {
    val transition = updateTransition(targetState = item.isExpanded, label = "")

    Column(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
        Row(
            modifier = Modifier
                .height(70.dp)
                .fillMaxWidth()
                .clickable { onClickExpandedItem.invoke(item) }
                .padding(horizontal = 10.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = item.time.times(1000).toDateTime(Constants.DateFormat.EE_MM_dd),
                    modifier = Modifier.padding(bottom = 5.dp),
                    style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.secondary),
                )

                Text(
                    text = item.weatherType.desc,
                    style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.inversePrimary),
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .padding(end = 10.dp),
                    painter = painterResource(id = item.weatherType.iconRes),
                    contentDescription = null,
                )

                Column {
                    Text(
                        text = stringResource(id = R.string.degrees_c, item.tempMax.toString()),
                        modifier = Modifier.padding(bottom = 5.dp),
                        style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.secondary),
                    )

                    Text(
                        text = stringResource(id = R.string.degrees_c, item.tempMin.toString()),
                        style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.inversePrimary),
                    )
                }

                transition.AnimatedContent(transitionSpec = {
                    if (!targetState) {
                        slideInVertically { height -> height } + fadeIn() with slideOutVertically { height -> -height } + fadeOut()
                    } else {
                        slideInVertically { height -> -height } + fadeIn() with slideOutVertically { height -> height } + fadeOut()
                    }.using(
                        SizeTransform(clip = false)
                    )
                }) { state ->
                    Icon(
                        imageVector = if (!state) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 10.dp),
                    )
                }
            }
        }

        transition.AnimatedVisibility(
            visible = { it },
            enter = expandVertically(),
            exit = shrinkVertically(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                WeatherInformation(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp),
                    title = stringResource(id = R.string.wind),
                    description = stringResource(id = R.string.meter_per_second, item.windSpeed),
                )

                WeatherInformation(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp),
                    title = stringResource(id = R.string.humidity),
                    description = stringResource(id = R.string.humidity, item.humidity),
                )
            }
        }

        Divider(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.inversePrimary)
    }
}

@Composable
fun WeatherInformation(
    modifier: Modifier = Modifier,
    title: String = "",
    description: String = "",
) {
    Row(modifier = modifier) {
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.inversePrimary),
        )

        Text(
            text = description,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.secondary),
        )
    }
}