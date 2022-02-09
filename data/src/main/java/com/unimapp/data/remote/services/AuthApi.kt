package com.unimapp.data.remote.services

import com.unimapp.domain.base.ApiResult
import com.unimapp.domain.entities.auth.LoginRequest
import com.unimapp.domain.entities.auth.LoginResponse
import com.unimapp.domain.entities.auth.RegisterResponse
import com.unimapp.domain.entities.auth.RegistrationRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {

    @POST("/api/v1/auth/register")
    suspend fun registerUser(@Body registrationRequest: RegistrationRequest, @Query("lang") lang: String): ApiResult<RegisterResponse>

    @POST("/api/v1/auth/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): ApiResult<LoginResponse>

}