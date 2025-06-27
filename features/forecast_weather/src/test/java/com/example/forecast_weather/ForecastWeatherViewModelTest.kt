package com.example.forecast_weather

import com.example.core.util.EmptyResponseException
import com.example.core.util.NetworkErrorException
import com.example.core.util.ServerErrorException
import com.example.core.util.UnknownErrorException
import org.junit.Assert.*

import com.example.forecast_weather.util.MainCoroutineRule
import com.example.forecast_weather.util.TestDispatchers
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ForecastWeatherViewModelTest {

    private lateinit var viewModel: ForecastWeatherViewModel
    private lateinit var getForecastWeatherUseCase: GetForecastWeatherUseCase
    private lateinit var testDispatcher: TestDispatchers
    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()
    @Before
    fun setUp() {
        getForecastWeatherUseCase = mockk()
        testDispatcher = TestDispatchers()

        viewModel = ForecastWeatherViewModel(getForecastWeatherUseCase, testDispatcher)
    }

    @Test
    fun `getForecastWeather() when successful call, then should update state`() = runTest {
        // Given
        val location = "TestLocation"
        val forecastWeatherList = listOf(
            ForecastWeather(
                maxTemperature = 30.0,
                minTemperature = 20.0,
                date = "2024-02-28T16:17:00-05:00",
                condition = "Clear",
                weatherIcon = 1,
            ),
            ForecastWeather(
                maxTemperature = 30.0,
                minTemperature = 20.0,
                date = "2024-02-28T16:17:00-05:00",
                condition = "Clear",
                weatherIcon = 1,
            ),
        )

        // Mock successful use case call
        coEvery { getForecastWeatherUseCase(location) } returns forecastWeatherList

        // When
        viewModel.getForecastWeather(location)

        // Then
        assertEquals(true, viewModel.state.value.isLoading) // Check loading state

        delay(10L)

        // Check final state
        assertEquals(false, viewModel.state.value.isLoading)
        assertEquals(forecastWeatherList.size, viewModel.state.value.weatherList.size)
    }

    @Test
    fun `getForecastWeather() when use case throws EmptyResponseException, then should update state with EmptyResponse error`() =
        runTest {
            // Given
            val location = "TestLocation"

            // Mock use case to throw EmptyResponseException
            coEvery { getForecastWeatherUseCase(location) } throws EmptyResponseException()

            // When
            viewModel.getForecastWeather(location)

            delay(10L)

            // Check final state
            assertEquals(false, viewModel.state.value.isLoading)
            assertEquals(true, viewModel.state.value.isError)
        }

    @Test
    fun `getForecastWeather() when use case throws NetworkErrorException, then should update state with NetworkError`() =
        runTest {
            // Given
            val location = "TestLocation"

            // Mock use case to throw NetworkErrorException
            coEvery { getForecastWeatherUseCase(location) } throws NetworkErrorException("Network error")

            // When
            viewModel.getForecastWeather(location)

            delay(10L)

            // Check final state
            assertEquals(false, viewModel.state.value.isLoading)
            assertEquals(true, viewModel.state.value.isError)
        }

    @Test
    fun `getForecastWeather() when use case throws ServerErrorException, then should update state with ServerError`() =
        runTest {
            // Given
            val location = "TestLocation"

            // Mock use case to throw ServerErrorException
            coEvery { getForecastWeatherUseCase(location) } throws ServerErrorException("Server error")

            // When
            viewModel.getForecastWeather(location)

            delay(10L)

            // Check final state
            assertEquals(false, viewModel.state.value.isLoading)
            assertEquals(true, viewModel.state.value.isError)
        }

    @Test
    fun `getForecastWeather() when use case throws UnknownErrorException, then should update state with UnknownError`() =
        runTest {
            // Given
            val location = "TestLocation"

            // Mock use case to throw UnknownErrorException
            coEvery { getForecastWeatherUseCase(location) } throws UnknownErrorException("Unknown error")

            // When
            viewModel.getForecastWeather(location)

            delay(10L)

            // Check final state
            assertEquals(false, viewModel.state.value.isLoading)
            assertEquals(true, viewModel.state.value.isError)
        }
    @Test
    fun `getForecastWeather() when in progress, then should update state with loading state`() = runTest {
        // Given
        val location = "TestLocation"

        coEvery { getForecastWeatherUseCase(location) } coAnswers {
            delay(5000)
            emptyList()
        }

        // When
        viewModel.getForecastWeather(location)
        delay(6000)

        // Check final state
        assertEquals(false, viewModel.state.value.isLoading)
    }


}
