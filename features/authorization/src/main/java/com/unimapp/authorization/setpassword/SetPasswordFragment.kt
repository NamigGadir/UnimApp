package com.unimapp.authorization.setpassword

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.unimapp.core.BaseFragment
import com.unimapp.authorization.R
import com.unimapp.authorization.databinding.FragmentSetPasswordBinding
import com.unimapp.common.extensions.onTextChanged
import com.unimapp.common.extensions.toast

class SetPasswordFragment : BaseFragment<SetPasswordViewModel, FragmentSetPasswordBinding, Unit, SetPasswordEvent>() {

    val arguments by navArgs<SetPasswordFragmentArgs>()
    override fun getViewModelClass() = SetPasswordViewModel::class.java

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSetPasswordBinding
        get() = FragmentSetPasswordBinding::inflate

    override val onViewInit: FragmentSetPasswordBinding.() -> Unit = {
        setPassword.onTextChanged {
            onTextChanged()
        }

        setPasswordRetype.onTextChanged {
            onTextChanged()
        }
        continueButton.setOnClickListener {
            val registerRequest = arguments.requestInfo
            registerRequest.password = setPassword.editText?.text.toString()
            viewmodel.registerUser(registerRequest)
        }
    }

    private fun onTextChanged() {
        withBinding {
            val password = setPassword.editText?.text.toString()
            val retypePassword = setPasswordRetype.editText?.text.toString()
            viewmodel.checkPassword(password, retypePassword)
        }
    }

    override fun onEventUpdate(event: SetPasswordEvent) {
        withBinding {
            when (event) {
                is SetPasswordEvent.TextValidator -> {
                    if (event.isSymbolValid) setCheckCorrect(symbolChecker) else setCheckError(symbolChecker)
                    if (event.isUpperCaseValid) setCheckCorrect(uppercaseChecker) else setCheckError(uppercaseChecker)
                    if (event.isCharacterValid) setCheckCorrect(characterChecker) else setCheckError(characterChecker)
                    binding.continueButton.isEnabled = event.isCharacterValid && event.isSymbolValid && event.isUpperCaseValid
                }
                is SetPasswordEvent.OnPasswordNotSame -> {
                    setCheckError(symbolChecker)
                    setCheckError(uppercaseChecker)
                    setCheckError(characterChecker)
                    binding.continueButton.isEnabled = false
                }
                is SetPasswordEvent.RegisterResult -> {
                    onRegisterSuccess()
                }
            }
        }
    }

    private fun onRegisterSuccess() {
        findNavController().navigate(SetPasswordFragmentDirections.actionSetPasswordFragmentToSignInWithEmailFragment())
    }

    private fun setCheckError(textview: TextView) {
        textview.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_checker_red, 0, 0, 0)
    }

    private fun setCheckCorrect(textview: TextView) {
        textview.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_checker_green, 0, 0, 0)
    }
}



