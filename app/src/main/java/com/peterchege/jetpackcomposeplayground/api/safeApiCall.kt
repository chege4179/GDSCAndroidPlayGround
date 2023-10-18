package com.peterchege.jetpackcomposeplayground.api

import com.peterchege.jetpackcomposeplayground.util.NetworkResult
import retrofit2.HttpException
import retrofit2.Response

suspend fun <T : Any> safeApiCall(
    execute: suspend () -> Response<T>
): NetworkResult<T> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            NetworkResult.Success(body)
        } else {
            NetworkResult.Error(code = response.code(), message = response.message())
        }
    } catch (e: HttpException) {
        NetworkResult.Error(code = e.code(), message = e.message())
    } catch (e: Throwable) {
//        Timber.tag("Network error throwable").d(e)
        NetworkResult.Exception(e)
    }
}