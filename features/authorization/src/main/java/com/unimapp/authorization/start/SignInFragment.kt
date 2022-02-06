package com.unimapp.authorization.start

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.unimapp.core.BaseFragment
import com.unimapp.authorization.R
import com.unimapp.authorization.databinding.FragmentSignInBinding
import com.unimapp.authorization.siginwithemail.AuthState
import com.unimapp.common.extensions.onClick
import com.unimapp.common.extensions.underline
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<SignInViewModel, FragmentSignInBinding, AuthState, Unit>() {

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