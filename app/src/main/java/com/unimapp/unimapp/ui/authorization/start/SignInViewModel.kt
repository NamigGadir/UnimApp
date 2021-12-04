package com.unimapp.unimapp.ui.authorization.start

import com.unimapp.unimapp.core.BaseViewModel
import com.unimapp.unimapp.ui.authorization.siginwithemail.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : BaseViewModel<AuthState, Unit>() {

}