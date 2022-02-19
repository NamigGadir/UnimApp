package com.unimapp.domain.repository

import com.unimapp.domain.entities.feed.Comment
import com.unimapp.domain.entities.feed.Feed
import com.unimapp.domain.entities.feed.UploadImageResponse
import kotlinx.coroutines.flow.Flow
import java.io.File

interface FeedRepository {
    fun loadFeed(): Flow<List<Feed>>
    fun loadComments(): Flow<List<Comment>>
    suspend fun uploadImage(file: File): UploadImageResponse
}