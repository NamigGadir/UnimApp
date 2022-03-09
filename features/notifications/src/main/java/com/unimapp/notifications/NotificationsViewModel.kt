package com.unimapp.notifications

import androidx.lifecycle.viewModelScope
import com.unimapp.core.BaseViewModel
import com.unimapp.domain.entities.notifications.ActionType
import com.unimapp.domain.entities.notifications.NotificationItem
import com.unimapp.domain.entities.notifications.NotificationTypes
import com.unimapp.domain.usecases.notification.AnswerFriendRequestUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

class NotificationsViewModel @Inject constructor(
    private val answerFriendRequestUseCase: AnswerFriendRequestUseCase
) : BaseViewModel<NotificationsState, Unit>() {

    init {
        getNotifications()
    }

    private fun getNotifications() {
        viewModelScope.launch {
            delay(2000)
            postState(NotificationsState.OnNotification(notificationsList))
        }
    }

    fun answerFriendRequest(notificationId: String, isAccepted: Boolean) {
        invokeRequest(AnswerFriendRequestUseCase.Params(notificationId, isAccepted), answerFriendRequestUseCase) {

        }
    }

    private val notificationsList = listOf(
        NotificationItem(
            "1",
            NotificationTypes.SIMPLE, "Orxan AZM", "https://upload.wikimedia.org/wikipedia/commons/e/eb/Jeff_Bell_profile_photo.jpg",
            ActionType.LIKE, System.currentTimeMillis() - Random(500000).nextLong()
        ),
        NotificationItem(
            "2",
            NotificationTypes.SIMPLE, "Orxan AZM", "https://upload.wikimedia.org/wikipedia/commons/e/eb/Jeff_Bell_profile_photo.jpg",
            ActionType.COMMENT, System.currentTimeMillis() - Random(500000).nextLong(), "Salamlar"
        ),
        NotificationItem(
            "3",
            NotificationTypes.SIMPLE, "Orxan AZM", "https://upload.wikimedia.org/wikipedia/commons/e/eb/Jeff_Bell_profile_photo.jpg",
            ActionType.LIKE, System.currentTimeMillis() - Random(500000).nextLong()
        ),
        NotificationItem(
            "4",
            NotificationTypes.FRIEND, "Orxan AZM", "https://upload.wikimedia.org/wikipedia/commons/e/eb/Jeff_Bell_profile_photo.jpg",
            ActionType.FRIEND_REQUEST, System.currentTimeMillis() - Random(500000).nextLong(), speciality = "Qaynaqci", university = "Nesimi Bazari"
        )
    )
}


sealed class NotificationsState() {
    data class OnNotification(val list: List<NotificationItem>) : NotificationsState()
}