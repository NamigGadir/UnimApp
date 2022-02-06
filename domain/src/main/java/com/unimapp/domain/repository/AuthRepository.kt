package com.unimapp.domain.repository

import com.unimapp.domain.base.ApiResult
import com.unimapp.domain.entities.auth.Faculty
import com.unimapp.domain.entities.auth.Interest
import com.unimapp.domain.entities.auth.University

interface AuthRepository {
    suspend fun getInterests(): ApiResult<List<Interest>>
    suspend fun getUniversities(): ApiResult<List<University>>
    suspend fun getFaculties(): ApiResult<List<Faculty>>
}