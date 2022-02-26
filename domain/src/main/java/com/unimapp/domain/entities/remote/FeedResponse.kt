package com.unimapp.domain.entities.remote

import com.unimapp.domain.entities.feed.Feed
import com.unimapp.domain.entities.feed.FeedType

class FeedResponse(
    val feedId: Long? = null,
    val feedCaption: String? = null,
    val feedType: String? = null,
    val feedCommentCount: Int? = null,
    val feedSharedCount: Int? = null,
    val feedLikeCount: Int? = null,
    val createdAt: Long? = null,
    val feedUserResponseDto: FeedUser? = null
) {
    fun toDomain(): Feed {
        return Feed(
            feedId = feedId ?: -1,
            feedCaption = feedCaption ?: "",
            feedType = FeedType.getFeedType(feedType ?: ""),
            feedReactions = arrayListOf(),
            feedCommentCount = feedCommentCount ?: 0,
            feedSharedCount = feedSharedCount ?: 0,
            feedLikeCount = feedLikeCount ?: 0,
            createdAt = createdAt ?: 0,
            feedUser = feedUserResponseDto
        )
    }
}

class FeedUser(
    val id: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val universityName: String? = null
)