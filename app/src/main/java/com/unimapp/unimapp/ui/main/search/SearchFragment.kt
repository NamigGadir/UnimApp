package com.unimapp.unimapp.ui.main.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.unimapp.common.extensions.fakeArrayListOf
import com.unimapp.uitoolkit.R
import com.unimapp.uitoolkit.extensions.addDivider
import com.unimapp.uitoolkit.extensions.withSingleAdapter
import com.unimapp.core.BaseFragment
import com.unimapp.unimapp.databinding.FragmentSearchBinding
import com.unimapp.unimapp.databinding.SearchListItemBinding
import com.unimapp.home.home.HomePageState
import com.unimapp.home.home.HomePageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<HomePageViewModel, FragmentSearchBinding, HomePageState, Unit>() {

    override fun getViewModelClass() = HomePageViewModel::class.java

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding
        get() = FragmentSearchBinding::inflate

    override val onViewInit: FragmentSearchBinding.() -> Unit = {
        val list = fakeArrayListOf(String::class.java, 33)
        searchResultList.withSingleAdapter(
            list,
            bindingCallback = { viewGroup: ViewGroup?, attachToParent: Boolean ->
                SearchListItemBinding.inflate(LayoutInflater.from(context), viewGroup, attachToParent)
            }, onBindView = { data ->
                data
            }, onItemClick = {

            }).addDivider(height = 3, color = ContextCompat.getColor(requireContext(), R.color.unim_divider_color))
        binding.customSearchView.setOnSearchAction {
            it
        }
    }
}



