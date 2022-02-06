package com.unimapp.authorization.setpassword

import com.ingress.core.BaseViewModel
import com.unimapp.domain.validators.CharacterValidator
import com.unimapp.domain.validators.UpperCaseValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SetPasswordViewModel @Inject constructor(
    private val upperCaseValidator: UpperCaseValidator,
    private val characterValidator: CharacterValidator
) : BaseViewModel<Unit, SetPasswordEvent>() {

    fun checkPassword(password: String, retypePassword: String) {
        val symbolLength: Boolean
        if (password.length < 8 || retypePassword.length < 8) {
            return
        } else
            symbolLength = true

        if (password != retypePassword) {
            postEvent(SetPasswordEvent.OnPasswordNotSame)
            return
        }
        postEvent(SetPasswordEvent.TextValidator(upperCaseValidator.isValid(password), symbolLength, characterValidator.isValid(password)))
    }
}

sealed class SetPasswordEvent {
    object OnPasswordNotSame : SetPasswordEvent()
    class TextValidator(val isUpperCaseValid: Boolean, val isSymbolValid: Boolean, val isCharacterValid: Boolean) : SetPasswordEvent()
}


