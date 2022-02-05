package com.unimapp.unimapp.ui.onboarding

import com.ingress.core.BaseViewModel
import com.unimapp.authorization.siginwithemail.AuthState
import com.unimapp.domain.repository.OnBoardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val onBoardingRepository: OnBoardingRepository
) : BaseViewModel<AuthState, Unit>() {

    fun setOnBoardingLookStatus(status: Boolean) {
        onBoardingRepository.setOnBoardingLookStatus(status)
    }
}