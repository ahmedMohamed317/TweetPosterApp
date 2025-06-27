package com.example.core.base


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.EmptyResponseException
import com.example.core.util.ErrorHandler
import com.example.core.util.NetworkErrorException
import com.example.core.util.ServerErrorException
import com.example.core.util.UnknownErrorException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<T>(initialState: T) : ViewModel() {
    protected val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()
    protected fun <T> tryToExecute(
        function: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (t: ErrorHandler) -> Unit,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) {
        viewModelScope.launch(dispatcher) {
            try {
                val result = function()
                onSuccess(result)
            } catch (exception: NetworkErrorException) {
                Log.d("BaseViewModel1", exception.toString())
                onError(ErrorHandler.NetworkError(exception.message.toString()))
            } catch (exception: EmptyResponseException) {
                Log.d("BaseViewModel2", exception.toString())
                onError(ErrorHandler.EmptyResponse(exception.message.toString()))
            } catch (exception: ServerErrorException) {
                Log.d("BaseViewMode3l", exception.toString())
                onError(ErrorHandler.ServerError(exception.message.toString()))
            } catch (exception: UnknownErrorException) {
                Log.d("BaseViewModel", exception.toString())
                onError(ErrorHandler.UnknownError(exception.message.toString()))
            }
        }

    }
}

