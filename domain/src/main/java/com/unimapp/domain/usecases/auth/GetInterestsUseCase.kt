package com.unimapp.domain.usecases.auth

import com.unimapp.domain.base.ApiContent
import com.unimapp.domain.base.ApiResult
import com.unimapp.domain.base.BaseUseCase
import com.unimapp.domain.base.RemoteResponse
import com.unimapp.domain.entities.auth.Interest
import com.unimapp.domain.repository.AuthRepository
import javax.inject.Inject

class GetInterestsUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase<Unit, ApiContent<List<Interest>>> {

    override suspend fun call(input: Unit): ApiResult<ApiContent<List<Interest>>> {
       return authRepository.getInterests()
    }
}