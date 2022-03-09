package com.unimapp.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import com.unimapp.common.extensions.asColorResource
import com.unimapp.common.extensions.dp
import com.unimapp.core.BaseFragment
import com.unimapp.domain.entities.notifications.NotificationItem
import com.unimapp.notifications.databinding.FragmentNotificationsBinding
import com.unimapp.uitoolkit.extensions.addDivider

class NotificationsFragment : BaseFragment<NotificationsViewModel, FragmentNotificationsBinding, NotificationsState, Unit>() {

    private lateinit var notificationsAdapter: NotificationsAdapter

    override fun getViewModelClass() = NotificationsViewModel::class.java

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNotificationsBinding
        get() = FragmentNotificationsBinding::inflate


    override val onViewInit: FragmentNotificationsBinding.() -> Unit = {
        notificationsAdapter = NotificationsAdapter { notificationItem, isAccepted ->
            viewmodel.answerFriendRequest(notificationItem.id, isAccepted)
        }
        notificationsList.adapter = notificationsAdapter
    }


    override fun onStateUpdate(state: NotificationsState) {
        when (state) {
            is NotificationsState.OnNotification -> onNotificationListUpdate(state.list)
        }
    }

    private fun onNotificationListUpdate(list: List<NotificationItem>) {
        notificationsAdapter.submitList(list)
    }

}
