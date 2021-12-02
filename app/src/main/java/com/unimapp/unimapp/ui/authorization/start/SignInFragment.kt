package com.unimapp.unimapp.ui.authorization.start

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.unimapp.common.extensions.onClick
import com.unimapp.common.extensions.underline
import com.unimapp.unimapp.R
import com.unimapp.unimapp.core.BaseFragment
import com.unimapp.unimapp.databinding.FragmentSignInBinding
import com.unimapp.unimapp.ui.authorization.siginwithemail.AuthState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<SignInViewModel, FragmentSignInBinding, AuthState>() {

    override fun getViewModelClass() = SignInViewModel::class.java

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSignInBinding
        get() = FragmentSignInBinding::inflate

    override val onViewInit: FragmentSignInBinding.() -> Unit = {
        signInWithEmail.onClick {
            findNavController().navigate(R.id.action_signInFragment_to_signInWithEmailFragment)
        }
        signUp.underline()
    }

    override fun onStateUpdate(state: AuthState) {

    }

}