package com.unimapp.domain.base

interface BaseSuspendCallUseCase<T, R> {

    suspend fun call(input: T): R

}