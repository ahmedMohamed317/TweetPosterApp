package com.example.current_weather.ui

import com.example.core.util.NetworkErrorException
import com.example.core.util.ServerErrorException
import com.example.core.util.UnknownErrorException
import com.example.current_weather.util.MainCoroutineRule
import com.example.current_weather.util.TestDispatchers
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CurrentWeatherViewModelTest {

    private lateinit var viewModel: CurrentWeatherViewModel
    private lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase
    private lateinit var testDispatcher: TestDispatchers

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    @Before
    fun setUp() {
        getCurrentWeatherUseCase = mockk()
        testDispatcher = TestDispatchers()

        viewModel = CurrentWeatherViewModel(getCurrentWeatherUseCase, testDispatcher)
    }

    @Test
    fun `getCurrentWeather() when successful call, then should update state`() = runTest {
        // Given
        val location = "TestLocation"
        val currentWeatherList = listOf(
            CurrentWeather(
                temperature = 25.0,
                condition = "Clear",
                weatherIcon = 1,
                date = "2024-02-28T16:17:00-05:00",
            )
        )

        // Mock successful use case call
        coEvery { getCurrentWeatherUseCase(location) } returns currentWeatherList

        // When
        viewModel.getCurrentWeather(location)

        // Then
        assertEquals(true, viewModel.state.value.isLoading) // Check loading state

        delay(1000L)

        // Check final state
        assertEquals(currentWeatherList.first().toUiModel(), viewModel.state.value.weather)
    }


    @Test
    fun `getCurrentWeather() when use case throws NetworkErrorException, then should update state with NetworkError`() =
        runTest {
            // Given
            val location = "TestLocation"

            // Mock use case to throw NetworkErrorException
            coEvery { getCurrentWeatherUseCase(location) } throws NetworkErrorException("Network error")

            // When
            viewModel.getCurrentWeather(location)

            delay(10L)

            // Check final state
            assertEquals(false, viewModel.state.value.isLoading)
            assertEquals(true, viewModel.state.value.isError)
        }

    @Test
    fun `getCurrentWeather() when use case throws ServerErrorException, then should update state with ServerError`() =
        runTest {
            // Given
            val location = "TestLocation"

            // Mock use case to throw ServerErrorException
            coEvery { getCurrentWeatherUseCase(location) } throws ServerErrorException("Server error")

            // When
            viewModel.getCurrentWeather(location)

            delay(10L)

            // Check final state
            assertEquals(false, viewModel.state.value.isLoading)
            assertEquals(true, viewModel.state.value.isError)
        }

    @Test
    fun `getCurrentWeather() when use case throws UnknownErrorException, then should update state with UnknownError`() =
        runTest {
            // Given
            val location = "TestLocation"

            // Mock use case to throw UnknownErrorException
            coEvery { getCurrentWeatherUseCase(location) } throws UnknownErrorException("Unknown error")

            // When
            viewModel.getCurrentWeather(location)

            delay(10L)

            // Check final state
            assertEquals(false, viewModel.state.value.isLoading)
            assertEquals(true, viewModel.state.value.isError)
        }
}
