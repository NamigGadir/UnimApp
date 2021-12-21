package com.unimapp.unimapp.ui.main.search

import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.TextView
import android.widget.TextView.BufferType
import com.unimapp.common.extensions.makeTextViewResizable
import com.unimapp.unimapp.core.BaseFragment
import com.unimapp.unimapp.databinding.FragmentSearchBinding
import com.unimapp.unimapp.ui.main.home.HomePageState
import com.unimapp.unimapp.ui.main.home.HomePageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<HomePageViewModel, FragmentSearchBinding, HomePageState, Unit>() {

    override fun getViewModelClass() = HomePageViewModel::class.java

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding
        get() = FragmentSearchBinding::inflate

    override val onViewInit: FragmentSearchBinding.() -> Unit = {
    }


}