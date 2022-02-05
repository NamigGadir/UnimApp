package com.unimapp.unimapp.ui.main.notifications

import com.ingress.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(

) : BaseViewModel<NotificationState, Unit>() {
}

sealed class NotificationState {
}