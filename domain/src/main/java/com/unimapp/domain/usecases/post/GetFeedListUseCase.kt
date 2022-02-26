package com.unimapp.domain.usecases.post

import com.unimapp.domain.base.BaseUseCase
import com.unimapp.domain.entities.feed.Feed
import com.unimapp.domain.repository.FeedRepository
import javax.inject.Inject

class GetFeedListUseCase @Inject constructor(
    private val feedRepository: FeedRepository
) : BaseUseCase<Unit, List<Feed>> {

    override suspend fun call(input: Unit): List<Feed> {
        return feedRepository.getFeedList()
    }
}