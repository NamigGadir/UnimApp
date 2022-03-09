package com.unimapp.domain.repository

interface NotificationsRepository {
    fun answerFriendRequest(notificationId: String, isAccepted: Boolean)
}