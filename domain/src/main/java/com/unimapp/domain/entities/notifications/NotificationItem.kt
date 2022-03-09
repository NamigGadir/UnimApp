package com.unimapp.domain.entities.notifications

class NotificationItem(
    val id: String,
    val itemType: NotificationTypes,
    val userName: String,
    val userImage: String,
    val actionType: ActionType,
    val notificationDate: Long,
    val commentTitle: String? = null,
    val studyLevel: String? = null,
    val speciality: String? = null,
    val university: String? = null
)

enum class NotificationTypes {
    SIMPLE,
    FRIEND
}

enum class ActionType {
    LIKE,
    COMMENT,
    FRIEND_REQUEST
}