package com.unimapp.data.remote.services

import com.unimapp.domain.entities.feed.UploadImageResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FeedApi {


    @Multipart
    @POST("/api/v1/files")
    suspend fun uploadFile(

        @Part file: MultipartBody.Part
    ): UploadImageResponse

}