package com.unimapp.unimapp.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.unimapp.unimapp.core.BaseFragment
import com.unimapp.unimapp.databinding.FragmentOnboardingBinding


class OnBoardingFragment : BaseFragment<BaseViewModel, FragmentOnboardingBinding>() {

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentOnboardingBinding
        get() = FragmentOnboardingBinding::inflate



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}