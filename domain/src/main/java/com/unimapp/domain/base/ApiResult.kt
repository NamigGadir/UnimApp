package com.unimapp.domain.base

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
class ApiResult<T>(
    @field:Json(name = "result")
    val result: ApiContent<T>
)

@Keep
class ApiContent<T>(
    @field:Json(name = "content")
    val content: T
)