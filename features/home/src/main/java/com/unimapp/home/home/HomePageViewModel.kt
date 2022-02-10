package com.unimapp.home.home

import androidx.lifecycle.viewModelScope
import com.unimapp.core.BaseViewModel
import com.unimapp.domain.entities.feed.Feed
import com.unimapp.domain.entities.feed.FeedReaction
import com.unimapp.domain.entities.feed.FeedReactionType
import com.unimapp.domain.repository.FeedRepository
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

    fun getReactions(feedId: Long, reactionType: FeedReactionType): List<FeedReaction> {
        return if (state.value is HomePageState.FeedList) {
            val reactionList = (state.value as HomePageState.FeedList).feedList.find { it.feedId == feedId }?.feedReactions
            return if (reactionType == FeedReactionType.NOT_SPECIFIED)
                reactionList ?: arrayListOf()
            else
                reactionList?.filter { it.reactionType == reactionType } ?: arrayListOf()
        } else
            arrayListOf()
    }
}


sealed class HomePageState {
    class FeedList(val feedList: List<Feed>) : HomePageState()
}