package com.unimapp.data.repository.onboarding

import com.unimapp.data.repository.di.annotations.MainSharedPrefs
import com.unimapp.data.repository.prefs.base.BasePreferences
import com.unimapp.data.repository.util.SharedConstants
import javax.inject.Inject

class OnBoardingRepositoryImpl @Inject constructor(
    @MainSharedPrefs private val mainSharedPreferences: BasePreferences
) : OnBoardingRepository {

    override fun setOnBoardingLookStatus(haveLooked: Boolean) {
        mainSharedPreferences.set(SharedConstants.ONBOARDING_HAVE_LOOKED, haveLooked)
    }

    override fun isOnBoardingFinished() = mainSharedPreferences.get(SharedConstants.ONBOARDING_HAVE_LOOKED, false)

}