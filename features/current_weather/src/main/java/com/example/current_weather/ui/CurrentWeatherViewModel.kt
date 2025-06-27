package com.example.current_weather.ui

import com.example.core.util.ErrorHandler
import com.example.core.base.BaseViewModel
import com.example.core.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : BaseViewModel<CurrentWeatherUiState>(CurrentWeatherUiState()) {

    fun getCurrentWeather(location: String) {
        _state.update { it.copy(isLoading = true, location = location) }
        tryToExecute(
            {
                getCurrentWeatherUseCase(location)
            }, ::onSuccessfulFetch,
            ::onError,
            dispatcherProvider.io
        )
    }

    private fun onSuccessfulFetch(currentWeather: List<CurrentWeather>) {
        _state.update {
            it.copy(
                isLoading = false,
                weather = currentWeather.first().toUiModel()
            )
        }
    }

    private fun onError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error, isError = true) }
    }
}