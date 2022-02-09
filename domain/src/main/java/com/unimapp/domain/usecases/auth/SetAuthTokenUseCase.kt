package com.unimapp.domain.usecases.auth

import com.unimapp.domain.base.BaseSuspendCallUseCase
import com.unimapp.domain.repository.AuthRepository
import javax.inject.Inject

class SetAuthTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseSuspendCallUseCase<String, Unit> {

    override suspend fun call(input: String) {
        return authRepository.setAuthToken(input)
    }
}