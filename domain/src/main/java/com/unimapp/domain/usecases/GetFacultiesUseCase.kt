package com.unimapp.domain.usecases

import com.unimapp.domain.base.ApiResult
import com.unimapp.domain.base.BaseUseCase
import com.unimapp.domain.base.RemoteResponse
import com.unimapp.domain.entities.auth.Faculty
import com.unimapp.domain.entities.auth.Interest
import com.unimapp.domain.entities.auth.University
import com.unimapp.domain.repository.AuthRepository
import javax.inject.Inject

class GetFacultiesUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase<Unit, List<Faculty>> {

    override suspend fun call(input: Unit): ApiResult<List<Faculty>> {
       return authRepository.getFaculties()
    }
}