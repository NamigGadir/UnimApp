package com.unimapp.domain.base

interface BaseUseCase<T, R> {
    suspend operator fun invoke(input: T): R
}