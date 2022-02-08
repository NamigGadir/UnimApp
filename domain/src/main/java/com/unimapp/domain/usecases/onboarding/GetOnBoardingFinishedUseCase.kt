package com.unimapp.domain.usecases.onboarding

import com.unimapp.domain.base.BaseSingleCallUseCase
import com.unimapp.domain.base.BaseSuspendCallUseCase
import com.unimapp.domain.repository.OnBoardingRepository
import javax.inject.Inject

class GetOnBoardingFinishedUseCase @Inject constructor(
    private val onBoardingRepository: OnBoardingRepository
) : BaseSingleCallUseCase<Unit, Boolean> {

    override fun call(input: Unit): Boolean {
        return onBoardingRepository.isOnBoardingFinished()
    }
}