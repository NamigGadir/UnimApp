package com.unimapp.unimapp.ui.onboarding

import com.unimapp.domain.repository.OnBoardingRepository
import com.unimapp.unimapp.core.BaseViewModel
import com.unimapp.unimapp.ui.authorization.siginwithemail.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val onBoardingRepository: OnBoardingRepository
) : BaseViewModel<AuthState>() {

    fun setOnBoardingLookStatus(status: Boolean) {
        onBoardingRepository.setOnBoardingLookStatus(status)
    }
}