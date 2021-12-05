package com.unimapp.unimapp.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.unimapp.common.extensions.underline
import com.unimapp.unimapp.core.BaseFragment
import com.unimapp.unimapp.databinding.FragmentHomePageBinding
import com.unimapp.unimapp.databinding.SignInWithEmailFragmentBinding
import com.unimapp.unimapp.ui.authorization.siginwithemail.AuthState
import com.unimapp.unimapp.ui.authorization.siginwithemail.SignInWithEmailViewModel

class HomePageFragment : BaseFragment<HomePageViewModel, FragmentHomePageBinding, Unit, Unit>() {

    override fun getViewModelClass() = HomePageViewModel::class.java

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomePageBinding
        get() = FragmentHomePageBinding::inflate


    override val onViewInit: FragmentHomePageBinding.() -> Unit = {

    }
}