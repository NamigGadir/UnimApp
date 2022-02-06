package com.unimapp.authorization.start

import com.unimapp.core.BaseViewModel
import com.unimapp.authorization.siginwithemail.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : BaseViewModel<AuthState, Unit>() {



}