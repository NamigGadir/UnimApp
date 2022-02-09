package com.unimapp.authorization.siginwithemail

import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import com.unimapp.authorization.R
import com.unimapp.core.BaseFragment
import com.unimapp.authorization.databinding.SignInWithEmailFragmentBinding
import com.unimapp.common.extensions.dp
import com.unimapp.common.extensions.toast
import com.unimapp.common.extensions.underline
import com.unimapp.core.DeeplinkNavigationTypes
import com.unimapp.core.deeplinkNavigate
import dagger.hilt.android.AndroidEntryPoint
import java.util.logging.Handler


@AndroidEntryPoint
class SignInWithEmailFragment :
    BaseFragment<SignInWithEmailViewModel, SignInWithEmailFragmentBinding, Unit, SignInEvent>() {

    override fun getViewModelClass() = SignInWithEmailViewModel::class.java

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> SignInWithEmailFragmentBinding
        get() = SignInWithEmailFragmentBinding::inflate


    override val onViewInit: SignInWithEmailFragmentBinding.() -> Unit = {
        forgotPassword.underline()
        signInButton.setOnClickListener {
            viewmodel.signIn(editLogin.text.toString(), editPassword.text.toString())
        }
        editLogin.doOnTextChanged { text, start, before, count ->
            validate()
        }
        editPassword.doOnTextChanged { text, start, before, count ->
            validate()
        }
        android.os.Handler().postDelayed({
            onSignIn(true)
        }, 3000)
    }

    private fun onSignIn(isSuccess: Boolean) {
        withBinding {
            if (isSuccess) navigateToHome() else createToast(incorrectSingInMessage)
        }
    }

    private fun navigateToHome() {
        findNavController().deeplinkNavigate(DeeplinkNavigationTypes.HOME_PAGE)
    }

    private fun validate() {
        withBinding {
            viewmodel.validateInput(editLogin.text.toString(), editPassword.text.toString())
        }
    }

    override fun onEventUpdate(event: SignInEvent) {
        when (event) {
            is SignInEvent.SignInValidator -> binding.signInButton.isEnabled = event.isCharacterValid
                    && event.isEmailValid
                    && event.isSymbolValid
                    && event.isUpperCaseValid
            is SignInEvent.SignInResultEvent -> onSignIn(event.isLoggedIn)
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