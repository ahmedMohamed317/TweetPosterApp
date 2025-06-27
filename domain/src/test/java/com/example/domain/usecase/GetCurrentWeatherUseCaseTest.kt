package com.example.domain.usecase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetCurrentWeatherUseCaseTest {

    private lateinit var repo: WeatherRepository
    private lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase

    @Before
    fun setup() {
        repo = mockk()
        getCurrentWeatherUseCase = GetCurrentWeatherUseCase(repo)
    }

    @Test
    fun `invoke() should return current weather list from repository`() = runTest {
        // Given
        val city = "TestCity"
        val expectedCurrentWeatherList = listOf(
            CurrentWeather(
                temperature = 25.0,
                condition = "Clear",
                weatherIcon = 1,
                date = "2024-02-28T16:17:00-05:00",
            ),
            CurrentWeather(
                temperature = 28.0,
                condition = "Cloudy",
                weatherIcon = 2,
                date = "2024-02-28T16:17:00-05:00",
            )
        )

        // Mock repository behavior
        coEvery { repo.getWeather(city) } returns expectedCurrentWeatherList

        // When
        val result = getCurrentWeatherUseCase(city)

        // Then
        assertEquals(expectedCurrentWeatherList, result)
    }
}
