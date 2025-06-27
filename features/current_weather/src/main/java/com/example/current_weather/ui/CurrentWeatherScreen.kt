package com.example.current_weather.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core.composables.ContentVisibility
import com.example.core.composables.Loading
import com.example.core.composables.NetworkError
import com.example.core.util.WeatherType

@Composable
fun CurrentWeatherScreen(
    state: CurrentWeatherUiState,
    onItemClicked: (location:String) -> Unit,
) {
    Loading(state = state.isLoading)
    NetworkError(state = state.isError, error = "Network Error")
    ContentVisibility(state = state.contentScreen()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            WeatherCard(state = state.weather)
            Button(
                colors = ButtonDefaults.textButtonColors(Color.Transparent),
                onClick = {  onItemClicked(state.location)},
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Display a 7-day forecast",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                )

            }
        }
    }

}

@Composable
fun WeatherCard(
    state: WeatherUiState
) {
    Card(
        shape = RoundedCornerShape(10.dp), modifier = Modifier.padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = state.date,
                modifier = Modifier.align(Alignment.End),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = WeatherType.fromWMO(state.iconWeather).iconRes),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.width(150.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = state.temperature,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = state.condition,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary,
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }

}
