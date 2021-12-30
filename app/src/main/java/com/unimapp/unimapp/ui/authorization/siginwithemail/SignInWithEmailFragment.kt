package com.unimapp.unimapp.ui.authorization.siginwithemail

import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.unimapp.common.extensions.dp
import com.unimapp.common.extensions.underline
import com.unimapp.unimapp.core.BaseFragment
import com.unimapp.unimapp.databinding.SignInWithEmailFragmentBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignInWithEmailFragment :
    BaseFragment<SignInWithEmailViewModel, SignInWithEmailFragmentBinding, AuthState, Unit>() {

    override fun getViewModelClass() = SignInWithEmailViewModel::class.java

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> SignInWithEmailFragmentBinding
        get() = SignInWithEmailFragmentBinding::inflate


    override val onViewInit: SignInWithEmailFragmentBinding.() -> Unit = {
        forgotPassword.underline()
        signInButton.setOnClickListener {
            viewmodel.signIn()
        }

    }

    override fun onStateUpdate(state: AuthState) {
        when (state) {
            is AuthState.SignInState -> {
                onSignIn(state.isLoggedIn)
            }
        }
    }

    fun onSignIn(isSuccess: Boolean) {
        withBinding {
            if (isSuccess) "LoggedIn" else createToast(incorrectSingInMessage)
        }
    }

    private fun createToast(inCorrectSignIn: TextView) {
        Snackbar.make(inCorrectSignIn, "Password or email is incorrect", Snackbar.LENGTH_LONG)
            .apply {
                anchorView = inCorrectSignIn
                setTextColor(Color.RED)
                setBackgroundTint(Color.parseColor("#f4cccc"))
                view.apply {
                    val params = layoutParams as FrameLayout.LayoutParams
                    params.setMargins(
                        35.dp,
                        60.dp,
                        37.dp,
                        588.dp
                    )
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                    layoutParams = params
                    (view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView).apply {
                        gravity = Gravity.CENTER
                        setTypeface(typeface, Typeface.BOLD)
                        setPadding(
                            60.dp,
                            20.dp,
                            20.dp,
                            20.dp
                        )
                        setTextSize(16f)
                    }
                }
            }.show()
    }
}