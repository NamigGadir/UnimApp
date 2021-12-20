package com.unimapp.domain.repository

import com.unimapp.domain.entities.feed.Feed
import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    fun loadFeed(): Flow<List<Feed>>
}