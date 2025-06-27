package com.example.forecast_weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core.composables.ContentVisibility
import com.example.core.composables.Loading
import com.example.core.composables.NetworkError
import com.example.core.util.WeatherType

@Composable
fun ForecastWeatherScreen(
    state: ForecastWeatherUiState
) {
    Loading(state = state.isLoading)
    NetworkError(state = state.isError, error = "Network Error")
    ContentVisibility(state = state.contentScreen()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)

        ) {
            items(state.weatherList.size) { index ->
                val forecastWeather= state.weatherList[index]
                ForecastWeatherItem(
                    condition = forecastWeather.condition,
                    maxTemperature = forecastWeather.maxTemperature,
                    date = forecastWeather.date,
                    iconWeather = forecastWeather.iconWeather
                ) {
                }
            }

        }
    }
}
@Composable
fun ForecastWeatherItem(
    condition: String,
    maxTemperature: String,
    date: String,
    iconWeather: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Image(
            modifier = Modifier
                .weight(0.15f)
                .aspectRatio(1f)
                .align(Alignment.Top)
                .clip(CircleShape),
            painter = painterResource(id = WeatherType.fromWMO(iconWeather).iconRes),
            contentDescription = null,
        )
        Column(modifier = Modifier
            .weight(0.8f)
            .padding(start = 16.dp)) {
            androidx.compose.material.Text(
                text = condition,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.padding(2.dp))
            androidx.compose.material.Text(
                text = maxTemperature,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.padding(2.dp))
            androidx.compose.material.Text(
                text = date,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

    }

}