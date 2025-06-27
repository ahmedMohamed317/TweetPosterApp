package com.example.forecast_weather

import android.util.Log
import com.example.core.util.ErrorHandler
import com.example.core.base.BaseViewModel
import com.example.core.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ForecastWeatherViewModel @Inject constructor(
    private val getForecastWeatherUseCase: GetForecastWeatherUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : BaseViewModel<ForecastWeatherUiState>(ForecastWeatherUiState()) {

    fun getForecastWeather(location: String) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            {
                getForecastWeatherUseCase(location)
            }, ::onSuccessfulFetch,
            ::onError,
            dispatcherProvider.io
        )
    }

    private fun onSuccessfulFetch(weatherList: List<ForecastWeather>) {
        _state.update {
            it.copy(
                isLoading = false,
                weatherList = weatherList.map { it.toUiModel() }
            )
        }
    }

    private fun onError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error, isError = true) }
    }
}