package com.example.current_weather.ui

import com.example.core.util.ErrorHandler
import com.example.core.util.toFormattedTime

data class CurrentWeatherUiState(
    val isLoading: Boolean = false,
    val error: ErrorHandler? = null,
    val isError: Boolean = false,
    val location: String = "",
    val weather: WeatherUiState = WeatherUiState()
)

data class WeatherUiState(
    val temperature: String = "",
    val iconWeather: Int = 0,
    val date: String = "",
    val condition: String = "",
)

fun CurrentWeather.toUiModel(): WeatherUiState {
    return WeatherUiState(
        temperature = this.temperature.toString(),
        iconWeather = this.weatherIcon,
        date = this.date.toFormattedTime(),
        condition = this.condition
    )
}

fun CurrentWeatherUiState.contentScreen() = !this.isLoading && !this.isError && this.weather.iconWeather != 0