package com.unimapp.domain.entities.auth

import androidx.annotation.Keep

@Keep
class LoginRequest(
    val email: String,
    val password: String
)