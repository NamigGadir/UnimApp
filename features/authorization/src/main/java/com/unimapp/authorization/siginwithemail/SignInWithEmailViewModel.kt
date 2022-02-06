package com.unimapp.authorization.siginwithemail

import com.unimapp.core.BaseViewModel
import com.unimapp.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInWithEmailViewModel @Inject constructor(
    val authRepository: AuthRepository
) : BaseViewModel<AuthState, Unit>() {

    fun signIn() {
        postState(AuthState.SignInState(false))
    }

}

sealed class AuthState {
    class SignInState(val isLoggedIn: Boolean) : AuthState()
}