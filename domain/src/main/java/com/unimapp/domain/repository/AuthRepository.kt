package com.unimapp.domain.repository

import com.unimapp.domain.base.ApiContent
import com.unimapp.domain.base.ApiResult
import com.unimapp.domain.entities.auth.*

interface AuthRepository {
    suspend fun getInterests(): ApiResult<ApiContent<List<Interest>>>
    suspend fun getUniversities(): ApiResult<ApiContent<List<University>>>
    suspend fun getFaculties(): ApiResult<ApiContent<List<Faculty>>>
    suspend fun registerUser(registrationRequest: RegistrationRequest): ApiResult<RegisterResponse>
    suspend fun setIsUserRegistered(isUserRegistered: Boolean)
    suspend fun setAuthToken(authToken: String)
    fun getAuthToken(): String
    fun isUserRegistered(): Boolean
}