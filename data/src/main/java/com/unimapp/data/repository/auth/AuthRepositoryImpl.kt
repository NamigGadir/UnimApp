package com.unimapp.data.repository.auth

import com.unimapp.data.remote.services.AuthApi
import com.unimapp.data.remote.services.MdServicesApi
import com.unimapp.data.util.Constants
import com.unimapp.domain.base.ApiResult
import com.unimapp.domain.entities.auth.Interest
import com.unimapp.domain.entities.auth.LoginModel
import com.unimapp.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val mdServicesApi: MdServicesApi
) : AuthRepository {

    override suspend fun getInterests(): ApiResult<List<Interest>> {
        return mdServicesApi.getInterest(Constants.DEFAULT_LANG)
    }
}