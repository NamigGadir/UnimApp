package com.unimapp.data.repository.onboarding

interface OnBoardingRepository {
    fun setOnBoardingLookStatus(haveLooked: Boolean)
    fun isOnBoardingFinished(): Boolean
}