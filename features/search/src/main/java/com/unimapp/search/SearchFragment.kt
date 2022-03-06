package com.unimapp.search

import android.view.LayoutInflater
import android.view.ViewGroup
import com.unimapp.common.extensions.asColorResource
import com.unimapp.common.extensions.dp
import com.unimapp.common.extensions.fakeArrayListOf
import com.unimapp.core.BaseFragment
import com.unimapp.search.databinding.FragmentSearchBinding
import com.unimapp.uitoolkit.adapters.UserInfo
import com.unimapp.uitoolkit.adapters.UserListAdapter
import com.unimapp.uitoolkit.extensions.addDivider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<SearchViewModel, FragmentSearchBinding, SearchState, Unit>() {
    private lateinit var searchAdapter: UserListAdapter

    override fun getViewModelClass() = SearchViewModel::class.java

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding
        get() = FragmentSearchBinding::inflate

    override val onViewInit: FragmentSearchBinding.() -> Unit = {
        searchAdapter = UserListAdapter()
        searchResultList.adapter = searchAdapter
        searchResultList.addDivider(color = R.color.unim_divider_color.asColorResource(requireContext()), height = 1.dp)
        customSearchView.setOnSearchAction {
            viewmodel.getSearchList(it)
        }
    }

    override fun onStateUpdate(state: SearchState) {
        when (state) {
            is SearchState.OnSearchResult -> {
                binding.customSearchView.setText(state.searchQuote)
                updateState(state.list)
            }
        }
    }

    private fun updateState(list: List<UserInfo>) {
        searchAdapter.submitList(list)
    }
}



