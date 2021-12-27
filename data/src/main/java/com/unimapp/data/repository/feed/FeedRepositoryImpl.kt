package com.unimapp.data.repository.feed

import com.unimapp.domain.entities.feed.*
import com.unimapp.domain.repository.FeedRepository
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

    private val reactionlist = listOf(
        FeedReaction("Namig", FeedReactionType.STAR,AddFriendType.IS_FRIEND),
        FeedReaction("Namig", FeedReactionType.STAR,AddFriendType.IS_REQUEST_AVAILABLE),
        FeedReaction("Namig", FeedReactionType.ANGRY,AddFriendType.IS_REQUESTED),
        FeedReaction("Namig", FeedReactionType.ANGRY,AddFriendType.IS_FRIEND),
        FeedReaction("Namig", FeedReactionType.LOVE,AddFriendType.IS_FRIEND),
        FeedReaction("Namig", FeedReactionType.LOVE,AddFriendType.IS_FRIEND),
        FeedReaction("Namig", FeedReactionType.LOVE,AddFriendType.IS_FRIEND),
    )

    private val list = listOf<Feed>(
        Feed(1, FeedType.DOC, reactionlist),
        Feed(21, FeedType.LINK, reactionlist),
        Feed(341, FeedType.MULTIPLE_IMAGE, reactionlist),
        Feed(451, FeedType.SINGLE_IMAGE, reactionlist),
        Feed(21, FeedType.SIMPLE, reactionlist),
        Feed(221, FeedType.SIMPLE, reactionlist),
        Feed(4331, FeedType.SIMPLE, reactionlist),
        Feed(23231, FeedType.SIMPLE, reactionlist),
        Feed(121, FeedType.SIMPLE, reactionlist),
        Feed(111, FeedType.SIMPLE, reactionlist),
        Feed(223231, FeedType.SIMPLE, reactionlist)
    )


}