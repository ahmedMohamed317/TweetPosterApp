package com.example.data.repository

import com.example.core.util.ApiConstants.ERROR_NETWORK
import com.example.core.util.ApiConstants.ERROR_UNEXPECTED
import com.example.core.util.ApiConstants.ERROR_UNKNOWN_SERVER
import com.example.core.util.EmptyResponseException
import com.example.core.util.NetworkErrorException
import com.example.core.util.ServerErrorException
import com.example.core.util.UnknownErrorException
import retrofit2.Response
import java.io.IOException

abstract class BaseRepository {
    protected suspend fun <T> wrapResponse(function: suspend () -> Response<T>):T {
        try {
            val response = function()
            if (response.isSuccessful) {
                val baseResponse = response.body()
                return baseResponse ?: throw EmptyResponseException()
            } else {
                val errorBody = response.errorBody()?.string()
                if (errorBody != null) {
                    throw ServerErrorException(errorBody)
                } else {
                    throw ServerErrorException(ERROR_UNKNOWN_SERVER)
                }
            }
        } catch (e: IOException) {
            throw NetworkErrorException(ERROR_NETWORK)
        } catch (e: Exception) {
            throw UnknownErrorException(e.message.toString())
        }
    }
}