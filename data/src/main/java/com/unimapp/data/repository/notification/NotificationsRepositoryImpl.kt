package com.unimapp.data.repository.notification

import com.unimapp.domain.repository.NotificationsRepository
import javax.inject.Inject

class NotificationsRepositoryImpl @Inject constructor() : NotificationsRepository {

    override fun answerFriendRequest(notificationId: String, isAccepted: Boolean) {

    }
}