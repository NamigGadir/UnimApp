package com.unimapp.unimapp.ui.onboarding

import com.unimapp.core.BaseViewModel
import com.unimapp.domain.usecases.onboarding.SetOnBoardingFinishedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val setOnBoardingFinishedUseCase: SetOnBoardingFinishedUseCase
) : BaseViewModel<Unit, Unit>() {

    fun setOnBoardingLookStatus(status: Boolean) {
        launch {
            setOnBoardingFinishedUseCase.call(SetOnBoardingFinishedUseCase.Params(status))
        }
    }
}