package com.unimapp.domain.usecases.auth

import com.unimapp.domain.base.BaseSuspendCallUseCase
import com.unimapp.domain.repository.AuthRepository
import javax.inject.Inject

class SetUserRegisteredUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseSuspendCallUseCase<SetUserRegisteredUseCase.Params, Unit> {

    override suspend fun call(input: Params) {
        return authRepository.setIsUserRegistered(input.isUserRegistered)
    }

    class Params(val isUserRegistered: Boolean)
}