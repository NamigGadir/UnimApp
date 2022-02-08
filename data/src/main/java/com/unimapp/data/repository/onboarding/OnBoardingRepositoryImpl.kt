package com.unimapp.data.repository.onboarding

import com.unimapp.data.di.annotations.MainSharedPrefs
import com.unimapp.data.prefs.base.BasePreferences
import com.unimapp.data.util.SharedConstants
import com.unimapp.domain.repository.OnBoardingRepository
import javax.inject.Inject

class OnBoardingRepositoryImpl @Inject constructor(
    @MainSharedPrefs private val mainSharedPreferences: BasePreferences
) : OnBoardingRepository {

    override fun setOnBoardingLookStatus(haveLooked: Boolean) {
        mainSharedPreferences.set(SharedConstants.ONBOARDING_HAVE_LOOKED, haveLooked)
    }

    override fun isOnBoardingFinished() = mainSharedPreferences.get(SharedConstants.ONBOARDING_HAVE_LOOKED, false)
}