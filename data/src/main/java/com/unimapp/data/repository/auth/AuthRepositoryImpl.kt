package com.unimapp.data.repository.auth

import com.unimapp.data.prefs.sharedprefs.AuthenticationPreferences
import com.unimapp.data.remote.services.AuthApi
import com.unimapp.data.remote.services.MdServicesApi
import com.unimapp.data.util.Constants
import com.unimapp.domain.base.ApiContent
import com.unimapp.domain.base.ApiResult
import com.unimapp.domain.entities.auth.*
import com.unimapp.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val mdServicesApi: MdServicesApi,
    private val authenticationPreferences: AuthenticationPreferences
) : AuthRepository {

    override suspend fun getInterests(): ApiResult<ApiContent<List<Interest>>> {
        return mdServicesApi.getInterest(Constants.DEFAULT_LANG)
    }

    override suspend fun getUniversities(): ApiResult<ApiContent<List<University>>> {
        return mdServicesApi.getUniversities(Constants.DEFAULT_LANG)
    }

    override suspend fun getFaculties(): ApiResult<ApiContent<List<Faculty>>> {
        return mdServicesApi.getFaculties(Constants.DEFAULT_LANG)
    }

    override suspend fun registerUser(registrationRequest: RegistrationRequest): ApiResult<RegisterResponse> {
        return authApi.registerUser(registrationRequest, Constants.DEFAULT_LANG)
    }

    override suspend fun setIsUserRegistered(isUserRegistered: Boolean) {
        authenticationPreferences.setIsRegistered(isUserRegistered)
    }

    override fun isUserRegistered(): Boolean {
        return authenticationPreferences.isRegistered()
    }

    override suspend fun setAuthToken(authToken: String) {
        authenticationPreferences.setToken(authToken)
    }

    override fun getAuthToken(): String {
        return authenticationPreferences.getToken()
    }

    override suspend fun loginUser(loginRequest: LoginRequest): ApiResult<LoginResponse> {
        return authApi.loginUser(loginRequest)
    }
}