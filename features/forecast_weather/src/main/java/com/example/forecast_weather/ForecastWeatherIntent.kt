package com.example.forecast_weather

sealed class ForecastWeatherIntent {
    data class FetchForecastWeather(val location: String) : ForecastWeatherIntent()
}