package com.unimapp.unimapp.ui.authorization.start

import androidx.lifecycle.viewModelScope
import com.unimapp.domain.base.BaseUseCase
import com.unimapp.domain.usecases.LoginUseCase
import com.unimapp.unimapp.core.BaseViewModel
import com.unimapp.unimapp.ui.authorization.siginwithemail.AuthState
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