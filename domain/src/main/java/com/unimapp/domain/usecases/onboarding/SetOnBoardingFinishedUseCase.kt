package com.unimapp.domain.usecases.onboarding

import com.unimapp.domain.base.BaseSuspendCallUseCase
import com.unimapp.domain.repository.OnBoardingRepository
import javax.inject.Inject

class SetOnBoardingFinishedUseCase @Inject constructor(
    private val onBoardingRepository: OnBoardingRepository
) : BaseSuspendCallUseCase<SetOnBoardingFinishedUseCase.Params, Unit> {

    override suspend fun call(input: Params) {
        return onBoardingRepository.setOnBoardingLookStatus(input.isUserRegistered)
    }

    class Params(val isUserRegistered: Boolean)
}