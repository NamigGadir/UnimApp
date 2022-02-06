package com.unimapp.authorization.setpassword

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import com.unimapp.core.BaseFragment
import com.unimapp.authorization.R
import com.unimapp.authorization.databinding.FragmentSetPasswordBinding

class SetPasswordFragment : BaseFragment<SetPasswordViewModel, FragmentSetPasswordBinding, Unit, SetPasswordEvent>() {

    override fun getViewModelClass() = SetPasswordViewModel::class.java

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSetPasswordBinding
        get() = FragmentSetPasswordBinding::inflate

    override val onViewInit: FragmentSetPasswordBinding.() -> Unit = {
        setPassword.editText?.doOnTextChanged { text, start, before, count ->
            onTextChanged()
        }

        setPasswordRetype.editText?.doOnTextChanged { text, start, before, count ->
            onTextChanged()
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
                }
                is SetPasswordEvent.OnPasswordNotSame -> {
                    setCheckError(symbolChecker)
                    setCheckError(uppercaseChecker)
                    setCheckError(characterChecker)
                }
            }
        }
    }

    private fun setCheckError(textview: TextView) {
        textview.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_checker_red, 0, 0, 0)
    }

    private fun setCheckCorrect(textview: TextView) {
        textview.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_checker_green, 0, 0, 0)
    }
}



