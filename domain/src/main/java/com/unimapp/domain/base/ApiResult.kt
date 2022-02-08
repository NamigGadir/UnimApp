package com.unimapp.domain.base

import androidx.annotation.Keep

@Keep
class ApiResult<T>(
    val result: T
)

@Keep
class ApiContent<T>(
    val content: T
)