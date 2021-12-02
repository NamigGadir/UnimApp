package com.unimapp.unimapp.ui.authorization.setpassword

import android.view.LayoutInflater
import android.view.ViewGroup
import com.unimapp.unimapp.core.BaseFragment
import com.unimapp.unimapp.databinding.FragmentSetPasswordBinding
import com.unimapp.unimapp.databinding.FragmentSignUpBinding
import com.unimapp.unimapp.ui.authorization.signup.SignUpViewModel

class SetPasswordFragment : BaseFragment<SetPasswordViewModel, FragmentSetPasswordBinding, Unit>() {

    override fun getViewModelClass() = SetPasswordViewModel::class.java

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSetPasswordBinding
        get() = FragmentSetPasswordBinding::inflate

    override val onViewInit: FragmentSetPasswordBinding.() -> Unit = {

    }

    override fun onStateUpdate(state: Unit) {

    }
}