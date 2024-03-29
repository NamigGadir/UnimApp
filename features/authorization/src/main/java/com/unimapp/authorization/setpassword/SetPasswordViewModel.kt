package com.unimapp.authorization.setpassword

import androidx.lifecycle.viewModelScope
import com.unimapp.core.BaseViewModel
import com.unimapp.domain.entities.auth.RegisterResponse
import com.unimapp.domain.entities.auth.RegistrationRequest
import com.unimapp.domain.usecases.auth.SetRegisterUseCase
import com.unimapp.domain.usecases.auth.SetUserRegisteredUseCase
import com.unimapp.domain.validators.CharacterValidator
import com.unimapp.domain.validators.UpperCaseValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetPasswordViewModel @Inject constructor(
    private val upperCaseValidator: UpperCaseValidator,
    private val characterValidator: CharacterValidator,
    private val registerUseCase: SetRegisterUseCase,
    private val registeredUseCase: SetUserRegisteredUseCase
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

    fun registerUser(registrationRequest: RegistrationRequest) {
        invokeRequest(SetRegisterUseCase.Params(registrationRequest), registerUseCase) {
            viewModelScope.launch {
                registeredUseCase.call(SetUserRegisteredUseCase.Params(true))
            }
            postEvent(SetPasswordEvent.RegisterResult(it))
        }
    }
}

sealed class SetPasswordEvent {
    object OnPasswordNotSame : SetPasswordEvent()
    class TextValidator(val isUpperCaseValid: Boolean, val isSymbolValid: Boolean, val isCharacterValid: Boolean) : SetPasswordEvent()
    class RegisterResult(val registerResponse: RegisterResponse) : SetPasswordEvent()
}

