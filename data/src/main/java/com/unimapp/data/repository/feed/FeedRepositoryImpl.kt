package com.unimapp.data.repository.feed

import com.unimapp.domain.entities.feed.Comment
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

    override fun loadComments() = flow {
        emit(
            comments
        )
    }

    private val comments = listOf(
        Comment(
            1, "sdasdasdads",
            9
        ),
        Comment(
            1, "sdasdasdads",
            2,
        ),
        Comment(
            2, "sdasdasdads",
            44
        ),
        Comment(
            3, "sdasdasdads",
        ),
        Comment(
            1, "sdasdasdads",
        ),

        )
    private val list = listOf<Feed>(
        Feed(FeedType.DOC),
        Feed(FeedType.LINK),
        Feed(FeedType.MULTIPLE_IMAGE),
        Feed(FeedType.SINGLE_IMAGE),
        Feed(FeedType.SIMPLE),
        Feed(FeedType.SIMPLE),
        Feed(FeedType.SIMPLE),
        Feed(FeedType.SIMPLE),
        Feed(FeedType.SIMPLE),
        Feed(FeedType.SIMPLE),
        Feed(FeedType.SIMPLE)
    )
}