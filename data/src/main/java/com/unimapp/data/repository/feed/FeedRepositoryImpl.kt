package com.unimapp.data.repository.feed

import com.unimapp.data.extensions.toBearer
import com.unimapp.data.prefs.sharedprefs.AuthenticationPreferences
import com.unimapp.domain.entities.remote.FeedResponse
import com.unimapp.data.remote.services.FeedApi
import com.unimapp.domain.base.ApiContent
import com.unimapp.domain.base.ApiResult
import com.unimapp.domain.entities.feed.*
import com.unimapp.domain.repository.FeedRepository
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedRepositoryImpl @Inject constructor(
    private val feedApi: FeedApi,
    private val authenticationPreferences: AuthenticationPreferences
) : FeedRepository {

    override suspend fun getFeedList(): List<Feed> {
        val token = authenticationPreferences.getToken().toBearer()
        return feedApi.getFeedList(token).result.content.map {
            it.toDomain()
        }
    }

    override fun loadComments() = flow {
        emit(
            comments
        )
    }

    override suspend fun uploadImage(file: File): UploadImageResponse {
        val requestFile: RequestBody = file.asRequestBody("multipart/form-data".toMediaType())
        val body: MultipartBody.Part = MultipartBody.Part.createFormData("file", file.name, requestFile)
        return feedApi.uploadFile(body)
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
        FeedReaction("Namig", FeedReactionType.STAR, AddFriendType.IS_FRIEND),
        FeedReaction("Namig", FeedReactionType.STAR, AddFriendType.IS_REQUEST_AVAILABLE),
        FeedReaction("Namig", FeedReactionType.ANGRY, AddFriendType.IS_REQUESTED),
        FeedReaction("Namig", FeedReactionType.ANGRY, AddFriendType.IS_FRIEND),
        FeedReaction("Namig", FeedReactionType.LOVE, AddFriendType.IS_FRIEND),
        FeedReaction("Namig", FeedReactionType.LOVE, AddFriendType.IS_FRIEND),
        FeedReaction("Namig", FeedReactionType.LOVE, AddFriendType.IS_FRIEND),
    )


}