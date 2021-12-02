package com.unimapp.data.repository.auth

import com.unimapp.data.remote.services.AuthApi
import com.unimapp.domain.entities.auth.LoginModel
import com.unimapp.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository {

    suspend fun login(loginModel: LoginModel) {
        authApi.login()
    }
}