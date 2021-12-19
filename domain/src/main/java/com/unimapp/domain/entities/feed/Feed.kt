package com.unimapp.domain.entities.feed

class Feed(
    val feedUser: FeedUser,
    val feedMessage: String,
    val feedType: FeedType,
    val feedReactions: FeedReaction
)

class FeedUser(
    val username: String,
    val userSpeciality: String,
    val userUniversity: String,
    val userDate: String,
    val userImage: String,
    val childUsers: List<String>
)

data class FeedReaction(
    val reactionUser: String,
    val reactionType: FeedReactionType
)

enum class FeedReactionType {
    STAR,
    HEART,
    ANGRY
}

enum class FeedType {
    DOC,
    LINK
}

