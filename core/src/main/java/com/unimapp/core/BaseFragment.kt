package com.unimapp.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<ViewModel : BaseViewModel<State, Event>, VB : ViewBinding, State, Event> : Fragment() {
    protected abstract val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> VB

    protected lateinit var viewmodel: ViewModel

    lateinit var binding: VB

    protected abstract fun getViewModelClass(): Class<ViewModel>

    private val progressDialog: CustomProgressDialog by lazy { CustomProgressDialog(requireContext()) }

    open fun onStateUpdate(state: State) {}

    open fun onEventUpdate(event: Event) {}

    private fun initViewModel() {
        viewmodel = ViewModelProvider(requireActivity())[getViewModelClass()]
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
        binding.root.findViewWithTag<Toolbar>("toolbar")?.let {
            NavigationUI.setupWithNavController(it, findNavController())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInitView(binding)
        onViewInit(binding)
        startObserver()
    }

    private fun startObserver() {
        viewmodel.state.observe(requireActivity()) {
            onStateUpdate(state = it)
        }
        viewmodel.event.observe(requireActivity()) {
            onEventUpdate(event = it)
        }
        viewmodel.errorHandler.observe(viewLifecycleOwner) {
            it
        }
        viewmodel.handleProgressBar.observe(viewLifecycleOwner, Observer {
            if (it)
                showProgressBar()
            else
                hideProgressBar()
        })
    }

    inline fun <R> withBinding(block: VB.() -> R): R {
        return binding.block()
    }

    private fun hideProgressBar() {
        progressDialog.dismiss()
    }

    private fun showProgressBar() {
        progressDialog.show()
    }

}