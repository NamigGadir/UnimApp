package com.unimapp.domain.entities.feed

import com.unimapp.domain.entities.remote.FeedUser

class Feed(
    val feedId: Long,
    val feedCaption: String,
    val feedType: FeedType,
    var feedReactions: List<FeedReaction>,
    val feedCommentCount: Int,
    val feedSharedCount: Int,
    val feedLikeCount: Int,
    val createdAt: Long,
    val feedUser: FeedUser?
)

data class FeedReaction(
    val reactionUser: String,
    val reactionType: FeedReactionType,
    val friendType: AddFriendType
)

enum class AddFriendType {
    IS_FRIEND,
    IS_REQUESTED,
    IS_REQUEST_AVAILABLE
}

enum class FeedReactionType {
    STAR,
    LOVE,
    ANGRY,
    CRY,
    NOT_SPECIFIED,
    COOL,
    CELEBRATE,
    HAHA,
    OMG;


}

enum class FeedType {
    SIMPLE,
    DOC,
    LINK,
    SINGLE_IMAGE,
    MULTIPLE_IMAGE;

    companion object {
        fun getFeedType(feedType: String): FeedType {
            values().forEach {
                if (it.name == feedType)
                    return it
            }
            return SIMPLE
        }
    }

}

