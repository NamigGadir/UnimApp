package com.unimapp.domain.usecases.auth

import com.unimapp.domain.base.BaseSingleCallUseCase
import com.unimapp.domain.base.BaseSuspendCallUseCase
import com.unimapp.domain.repository.AuthRepository
import com.unimapp.domain.repository.OnBoardingRepository
import javax.inject.Inject

class GetRegistrationFinishedUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseSingleCallUseCase<Unit, Boolean> {

    override fun call(input: Unit): Boolean {
        return authRepository.isUserRegistered()
    }
}