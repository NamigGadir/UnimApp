package com.unimapp.domain.base

import androidx.annotation.Keep

@Keep
class ApiResult<T>(
    val result: ApiContent<T>
)

@Keep
class ApiContent<T>(
    val content: T
)