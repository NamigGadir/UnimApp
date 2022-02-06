package com.unimapp.data.remote.services

import com.unimapp.domain.base.ApiResult
import com.unimapp.domain.entities.auth.Faculty
import com.unimapp.domain.entities.auth.Interest
import com.unimapp.domain.entities.auth.University
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Query

interface MdServicesApi {

    @GET("api/v1/md/faculties")
    suspend fun getFaculties(@Query("lang") lang: String): ApiResult<List<Faculty>>

    @GET("api/v1/md/interests")
    suspend fun getInterest(@Query("lang") lang: String): ApiResult<List<Interest>>

    @GET("api/v1/md/universities")
    suspend fun getUniversities(@Query("lang") lang: String): ApiResult<List<University>>

}