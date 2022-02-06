package com.unimapp.data.remote.services

import retrofit2.http.POST

interface AuthApi {

    @POST("/v1/auth/login")
    suspend fun login(): Any



}