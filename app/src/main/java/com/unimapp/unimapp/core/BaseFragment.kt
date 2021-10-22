package com.unimapp.unimapp.core

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.unimapp.unimapp.ui.onboarding.BaseViewModel

abstract class BaseFragment<ViewModel : BaseViewModel, B : ViewBinding> : Fragment() {
    protected abstract val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> B

    protected lateinit var viewmodel: ViewModel

}