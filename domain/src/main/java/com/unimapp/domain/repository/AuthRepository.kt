package com.unimapp.domain.repository

import com.unimapp.domain.base.ApiResult
import com.unimapp.domain.entities.auth.Interest

interface AuthRepository {
    suspend fun getInterests(): ApiResult<List<Interest>>
}