package com.unimapp.unimapp

import com.unimapp.core.BaseViewModel
import com.unimapp.domain.repository.OnBoardingRepository
import com.unimapp.domain.usecases.GetAuthTokenUseCase
import com.unimapp.domain.usecases.GetRegistrationFinishedUseCase
import com.unimapp.domain.usecases.onboarding.GetOnBoardingFinishedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getOnBoardingFinishedUseCase: GetOnBoardingFinishedUseCase,
    private val getRegistrationFinishedUseCase: GetRegistrationFinishedUseCase,
    private val getAuthTokenUseCase: GetAuthTokenUseCase
) : BaseViewModel<Unit, Unit>() {

    fun isOnBoardFinished(): Boolean {
        return getOnBoardingFinishedUseCase.call(Unit)
    }

    fun isRegisteredUser(): Boolean {
        return getRegistrationFinishedUseCase.call(Unit)
    }

    fun isAuthTokenAvailable(): Boolean {
        return getAuthTokenUseCase.call(Unit).isNotEmpty()
    }

}