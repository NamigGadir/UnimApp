package com.unimapp.domain.entities.auth

import androidx.annotation.Keep

@Keep
class LoginResponse(
    val username: String,
    val token: String
)