package com.unimapp.authorization.siginwithemail

import androidx.lifecycle.viewModelScope
import com.unimapp.core.BaseViewModel
import com.unimapp.domain.usecases.auth.GetAuthTokenUseCase
import com.unimapp.domain.usecases.auth.SetAuthTokenUseCase
import com.unimapp.domain.usecases.auth.SetLoginUseCase
import com.unimapp.domain.validators.CharacterValidator
import com.unimapp.domain.validators.EmailValidator
import com.unimapp.domain.validators.UpperCaseValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInWithEmailViewModel @Inject constructor(
    private val setLoginUseCase: SetLoginUseCase,
    private val upperCaseValidator: UpperCaseValidator,
    private val characterValidator: CharacterValidator,
    private val emailValidator: EmailValidator,
    private val setAuthTokenUseCase: SetAuthTokenUseCase
) : BaseViewModel<Unit, SignInEvent>() {

    fun signIn(userName: String, password: String) {
        invokeRequest(SetLoginUseCase.Params(userName, password), setLoginUseCase, onError = { _, _ ->
            postEvent(SignInEvent.SignInResultEvent(false))
        }) {
            launch {
                setAuthTokenUseCase.call(it.token)
            }
            postEvent(SignInEvent.SignInResultEvent(true))
        }
    }

    fun validateInput(userEmail: String, password: String) {
        val symbolLength: Boolean
        if (password.length < 8) {
            return
        } else
            symbolLength = true

        postEvent(SignInEvent.SignInValidator(emailValidator.isValid(userEmail), upperCaseValidator.isValid(password), symbolLength, characterValidator.isValid(password)))
    }
}


sealed class SignInEvent {
    class SignInValidator(val isEmailValid: Boolean, val isUpperCaseValid: Boolean, val isSymbolValid: Boolean, val isCharacterValid: Boolean) : SignInEvent()
    class SignInResultEvent(val isLoggedIn: Boolean) : SignInEvent()
}

