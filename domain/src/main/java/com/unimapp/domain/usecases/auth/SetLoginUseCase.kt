package com.unimapp.domain.usecases.auth

import com.unimapp.domain.base.ApiResult
import com.unimapp.domain.base.BaseUseCase
import com.unimapp.domain.entities.auth.*
import com.unimapp.domain.repository.AuthRepository
import javax.inject.Inject

class SetLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase<SetLoginUseCase.Params, LoginResponse> {

    override suspend fun call(input: Params): ApiResult<LoginResponse> {
        return authRepository.loginUser(LoginRequest(input.userName, input.password))
    }

    class Params(val userName: String, val password: String)
}