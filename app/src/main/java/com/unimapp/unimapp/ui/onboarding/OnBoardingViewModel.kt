package com.unimapp.unimapp.ui.onboarding

import android.util.Log
import com.unimapp.data.repository.onboarding.OnBoardingRepository
import com.unimapp.unimapp.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val onBoardingRepository: OnBoardingRepository
) : BaseViewModel() {

    fun setOnBoardingLookStatus(status: Boolean) {
        onBoardingRepository.setOnBoardingLookStatus(status)
    }
}