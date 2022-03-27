package com.unimapp.booksession

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.unimapp.booksession.databinding.FragmentBookSessionBinding
import com.unimapp.core.BaseFragment


class BookSessionFragment: BaseFragment<BookSessionViewModel, FragmentBookSessionBinding, Unit, Unit>() {


    override fun getViewModelClass() = BookSessionViewModel::class.java

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBookSessionBinding
        get() = FragmentBookSessionBinding::inflate


    override val onViewInit: FragmentBookSessionBinding.() -> Unit = {
    }


}
