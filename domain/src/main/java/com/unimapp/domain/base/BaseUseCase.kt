package com.unimapp.domain.base

import retrofit2.HttpException

interface BaseUseCase<T, R> {

    suspend fun call(input: T): ApiResult<R>

    suspend operator fun invoke(input: T): RemoteResponse<R> {
        return try {
            RemoteResponse.Success(call(input).result.content)
        } catch (httpException: HttpException) {
            RemoteResponse.NetworkError(httpException.code())
        } catch (e: Exception) {
            e
            RemoteResponse.NetworkError(0)
        }
    }
}