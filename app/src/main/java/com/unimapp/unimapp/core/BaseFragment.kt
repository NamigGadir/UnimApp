package com.unimapp.unimapp.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<ViewModel : BaseViewModel, VB : ViewBinding> : Fragment() {
    protected abstract val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> VB

    protected lateinit var viewmodel: ViewModel

    lateinit var binding: VB

    protected abstract fun getViewModelClass(): Class<ViewModel>

    private fun initViewModel() {
        viewmodel = ViewModelProvider(requireActivity()).get(getViewModelClass())
    }

    protected open val onViewInit: VB.() -> Unit = {
        //ignored this in base
    }

    open fun onInitView(binding: VB) {
        //ignored in base
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = onViewBinding(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInitView(binding)
        onViewInit(binding)
    }

    inline fun <R> withBinding(block: VB.() -> R): R {
        return binding.block()
    }

}