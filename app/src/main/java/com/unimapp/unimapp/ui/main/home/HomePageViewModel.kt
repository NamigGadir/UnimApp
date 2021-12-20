package com.unimapp.unimapp.ui.main.home

import androidx.lifecycle.viewModelScope
import com.unimapp.domain.entities.feed.Feed
import com.unimapp.domain.repository.FeedRepository
import com.unimapp.unimapp.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val repository: FeedRepository
) : BaseViewModel<HomePageState, Unit>() {

    fun loadFeed() {
        repository.loadFeed()
            .onEach {
                postState(HomePageState.FeedList(it))
            }.launchIn(viewModelScope)
    }
}


sealed class HomePageState {
    class FeedList(val feedList: List<Feed>) : HomePageState()
}