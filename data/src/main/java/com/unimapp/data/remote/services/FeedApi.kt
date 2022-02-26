package com.unimapp.data.remote.services

import com.unimapp.domain.entities.remote.FeedResponse
import com.unimapp.domain.base.ApiContent
import com.unimapp.domain.base.ApiResult
import com.unimapp.domain.entities.feed.UploadImageResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface FeedApi {


    @Multipart
    @POST("/api/v1/files")
    suspend fun uploadFile(

        @Part file: MultipartBody.Part
    ): UploadImageResponse

    @GET("/api/v1/feed")
    suspend fun getFeedList(
        @Header("Authorization") authToken: String
    ): ApiResult<ApiContent<List<FeedResponse>>>
}