package com.unimapp.domain.base

interface BaseValidator<T> {
    fun isValid(input: T): Boolean
}