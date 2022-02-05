package com.unimapp.authorization.start

import androidx.lifecycle.viewModelScope
import com.ingress.core.BaseViewModel
import com.unimapp.authorization.siginwithemail.AuthState
import com.unimapp.domain.usecases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : BaseViewModel<AuthState, Unit>() {

    fun login() {
        viewModelScope.launch {
            loginUseCase(LoginUseCase.Params("dddd", ""))
        }
    }

}