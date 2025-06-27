package com.example.core.util

sealed class ErrorHandler {
    data class NetworkError(val message: String) : ErrorHandler()
    data class EmptyResponse(val message: String) : ErrorHandler()
    data class ServerError(val message: String) : ErrorHandler()
    data class UnknownError(val message: String) : ErrorHandler()
}
