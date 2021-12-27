package com.unimapp.domain.entities.feed

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class Feed(
    val feedId: Long,
//    val feedMessage: String,
    val feedType: FeedType,
    var feedReactions: List<FeedReaction>
)

//class FeedUser(
//    val username: String,
//    val userSpeciality: String,
//    val userUniversity: String,
//    val userDate: String,
//    val userImage: String,
//    val childUsers: List<String>
//)
//

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
    MULTIPLE_IMAGE
}

