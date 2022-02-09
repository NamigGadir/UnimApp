package com.unimapp.domain.usecases.auth

import com.unimapp.domain.base.ApiResult
import com.unimapp.domain.base.BaseUseCase
import com.unimapp.domain.entities.auth.*
import com.unimapp.domain.repository.AuthRepository
import javax.inject.Inject

class SetRegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase<SetRegisterUseCase.Params, RegisterResponse> {

    override suspend fun call(input: Params): ApiResult<RegisterResponse> {
        return authRepository.registerUser(input.registrationRequest)
    }

    class Params(val registrationRequest: RegistrationRequest)
}