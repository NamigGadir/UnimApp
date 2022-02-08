package com.unimapp.domain.base

interface BaseSingleCallUseCase<T, R> {

    fun call(input: T): R

}