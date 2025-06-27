package com.example.forecast_weather

import com.example.core.util.ErrorHandler
import com.example.core.util.toCelsius
import com.example.core.util.toFormattedTime

data class ForecastWeatherUiState(
    val isLoading: Boolean = false,
    val error: ErrorHandler? = null,
    val isError: Boolean = false,
    val weatherList : List<ForecastUiState> = emptyList(),
)

data class ForecastUiState(
    val maxTemperature: String = "",
    val minTemperature: String = "",
    val iconWeather: Int = 0,
    val date: String = "",
    val condition: String = "",
)

fun ForecastWeather.toUiModel(): ForecastUiState {
    return ForecastUiState(
        maxTemperature = this.maxTemperature.toCelsius().toString(),
        minTemperature = this.minTemperature.toCelsius().toString(),
        iconWeather = this.weatherIcon,
        date = this.date.toFormattedTime(),
        condition = this.condition
    )
}

fun ForecastWeatherUiState.contentScreen() = !this.isLoading && !this.isError && this.weatherList.isNotEmpty()