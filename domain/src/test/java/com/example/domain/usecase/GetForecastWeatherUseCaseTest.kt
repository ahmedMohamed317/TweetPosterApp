package com.example.domain.usecase

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetForecastWeatherUseCaseTest {

    private lateinit var repo: WeatherRepository
    private lateinit var getForecastWeatherUseCase: GetForecastWeatherUseCase

    @Before
    fun setup() {
        repo = mockk()
        getForecastWeatherUseCase = GetForecastWeatherUseCase(repo)
    }

    @Test
    fun `invoke() should return forecast weather list from repository`() = runTest {
        // Given
        val city = "TestCity"
        val expectedForecastWeatherList = listOf(
            ForecastWeather(
                date = "2024-02-28",
                minTemperature = 20.0,
                maxTemperature = 30.0,
                condition = "Partly Cloudy",
                weatherIcon = 3
            ),
            ForecastWeather(
                date = "2024-02-29",
                minTemperature = 22.0,
                maxTemperature = 32.0,
                condition = "Sunny",
                weatherIcon = 1
            )
        )

        // Mock repository behavior
        coEvery { repo.getForecast(city) } returns expectedForecastWeatherList

        // When
        val result = getForecastWeatherUseCase(city)

        // Then
        assertEquals(expectedForecastWeatherList, result)
    }
}
