package com.unimapp.unimapp

import com.unimapp.core.BaseViewModel
import com.unimapp.domain.repository.OnBoardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val onBoardingRepository: OnBoardingRepository) : BaseViewModel<Unit, Unit>() {

    fun isOnBoardFinished(): Boolean {
        return onBoardingRepository.isOnBoardingFinished()
    }

    fun isRegisteredUser(): Boolean {
        return false
    }

}