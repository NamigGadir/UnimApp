package com.unimapp.domain.usecases

import com.unimapp.domain.base.ApiResult
import com.unimapp.domain.base.BaseUseCase
import com.unimapp.domain.base.RemoteResponse
import com.unimapp.domain.entities.auth.Interest
import com.unimapp.domain.entities.auth.University
import com.unimapp.domain.repository.AuthRepository
import javax.inject.Inject

class GetUniversitiesUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase<Unit, List<University>> {

    override suspend fun call(input: Unit): ApiResult<List<University>> {
       return authRepository.getUniversities()
    }
}