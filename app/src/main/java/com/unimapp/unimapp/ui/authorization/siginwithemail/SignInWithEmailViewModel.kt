package com.unimapp.unimapp.ui.authorization.siginwithemail

import com.unimapp.domain.repository.AuthRepository
import com.unimapp.unimapp.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInWithEmailViewModel @Inject constructor(
    authRepository: AuthRepository
) : BaseViewModel<AuthState, Unit>() {

    fun signIn() {
        postState(AuthState.SignInState(true))
    }

}

sealed class AuthState {
    class SignInState(val isLoggedIn: Boolean) : AuthState()
}