package com.unimapp.domain.usecases.auth

import com.unimapp.domain.base.BaseSingleCallUseCase
import com.unimapp.domain.base.BaseSuspendCallUseCase
import com.unimapp.domain.repository.AuthRepository
import com.unimapp.domain.repository.OnBoardingRepository
import javax.inject.Inject

class GetAuthTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseSingleCallUseCase<Unit, String> {

    override fun call(input: Unit): String {
        return authRepository.getAuthToken()
    }
}