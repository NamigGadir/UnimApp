package com.unimapp.domain.repository

interface OnBoardingRepository {
    fun setOnBoardingLookStatus(haveLooked: Boolean)
    fun isOnBoardingFinished(): Boolean
}