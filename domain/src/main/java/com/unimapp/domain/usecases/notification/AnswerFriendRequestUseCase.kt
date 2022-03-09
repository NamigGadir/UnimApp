package com.unimapp.domain.usecases.notification

import com.unimapp.domain.base.BaseUseCase
import com.unimapp.domain.repository.NotificationsRepository
import com.unimapp.domain.usecases.onboarding.UploadImageUseCase
import javax.inject.Inject

class AnswerFriendRequestUseCase @Inject constructor(
    private val notificationsRepository: NotificationsRepository
) : BaseUseCase<AnswerFriendRequestUseCase.Params, Unit> {

    override suspend fun call(input: Params) {
        return notificationsRepository.answerFriendRequest(input.notificationId, input.isAccepted)
    }

    class Params(val notificationId: String, val isAccepted: Boolean)
}