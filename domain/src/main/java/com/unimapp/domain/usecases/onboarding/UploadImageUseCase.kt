package com.unimapp.domain.usecases.onboarding

import com.unimapp.domain.base.BaseUseCase
import com.unimapp.domain.repository.FeedRepository
import java.io.File
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val feedRepository: FeedRepository
) : BaseUseCase<UploadImageUseCase.Params, Any> {

    override suspend fun call(input: Params): Any {
        return feedRepository.uploadImage(input.file)
    }

    class Params(val file: File)
}