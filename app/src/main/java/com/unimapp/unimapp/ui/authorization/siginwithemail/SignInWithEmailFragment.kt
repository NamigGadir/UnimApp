package com.unimapp.unimapp.ui.authorization.siginwithemail

import android.view.LayoutInflater
import android.view.ViewGroup
import com.unimapp.common.extensions.underline
import com.unimapp.unimapp.core.BaseFragment
import com.unimapp.unimapp.databinding.SignInWithEmailFragmentBinding

class SignInWithEmailFragment : BaseFragment<SignInWithEmailViewModel, SignInWithEmailFragmentBinding>() {

    override fun getViewModelClass() = SignInWithEmailViewModel::class.java

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> SignInWithEmailFragmentBinding
        get() = SignInWithEmailFragmentBinding::inflate


    override val onViewInit: SignInWithEmailFragmentBinding.() -> Unit = {
        forgotPassword.underline()
    }

}