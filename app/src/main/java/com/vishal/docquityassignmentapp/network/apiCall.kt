package com.vishal.docquityassignmentapp.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

private const val TAG = "apiCall"

suspend fun <T : Any> apiCall(networkCall: suspend () -> Response<T>): ApiResult<T> {

    val result = ApiResult<T>()
    withContext(Dispatchers.IO) {
        try {
            val response = networkCall()
            result.state = ResultState.SUCCESS
            result.result = response.body()
            Log.d(TAG,
                "${
                    response.raw().request.url.encodedPath
                }: Response: ${retrofitGson.toJson(result.result)}")
        } catch (e: Exception) {
            result.state = ResultState.FAILURE
            result.error = e
            result.errorMessage = e.message
            Log.d(TAG, "${result.error} message: ${result.errorMessage}")
        }
    }

    return result
}

class ApiResult<T>(
    var state: ResultState = ResultState.PROGRESS,
    var result: T? = null,
    var error: Throwable? = null,
    var errorMessage: String? = null,
)

enum class ResultState {
    PROGRESS, SUCCESS, FAILURE
}