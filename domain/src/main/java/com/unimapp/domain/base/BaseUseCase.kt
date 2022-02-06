package com.unimapp.domain.base

import retrofit2.HttpException

interface BaseUseCase<T, R> {

    suspend fun call(input: T): ApiResult<R>

    suspend operator fun invoke(input: T): RemoteResponse<R> {
        return try {
            RemoteResponse.Success(call(input).result.content)
        } catch (httpException: HttpException) {
            RemoteResponse.NetworkError(httpException.code(), httpException)
        } catch (e: Exception) {
            RemoteResponse.NetworkError(0, e)
        }
    }
}