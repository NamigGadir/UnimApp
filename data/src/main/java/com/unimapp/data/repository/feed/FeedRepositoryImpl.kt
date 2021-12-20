package com.unimapp.data.repository.feed

import com.unimapp.domain.entities.feed.Feed
import com.unimapp.domain.entities.feed.FeedType
import com.unimapp.domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedRepositoryImpl @Inject constructor() : FeedRepository {
    override fun loadFeed() = flow {
        emit(list)
    }

    private val list = listOf<Feed>(
        Feed(FeedType.DOC),
        Feed(FeedType.MULTIPLE_IMAGE),
        Feed(FeedType.SINGLE_IMAGE),
        Feed(FeedType.SIMPLE),
        Feed(FeedType.SIMPLE),
        Feed(FeedType.SIMPLE),
        Feed(FeedType.SIMPLE),
        Feed(FeedType.SIMPLE),
        Feed(FeedType.SIMPLE),
        Feed(FeedType.SIMPLE),
        Feed(FeedType.SIMPLE)
    )
}