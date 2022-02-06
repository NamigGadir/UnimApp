package com.unimapp.domain.base

sealed class RemoteResponse<out T> {
    data class Success<out T>(val result: T) : RemoteResponse<T>()
    data class NetworkError(val errorCode: Int) : RemoteResponse<Nothing>()
}
