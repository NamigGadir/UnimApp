package com.unimapp.unimapp

import com.unimapp.data.repository.onboarding.OnBoardingRepositoryImpl
import com.unimapp.unimapp.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val onBoardingRepositoryImpl: OnBoardingRepositoryImpl) : BaseViewModel() {

    fun isOnBoardFinished(): Boolean {
        return onBoardingRepositoryImpl.isOnBoardingFinished()
    }
}