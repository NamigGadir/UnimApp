package com.unimapp.unimapp.ui.main.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.unimapp.unimapp.R
import com.unimapp.unimapp.core.BaseFragment
import com.unimapp.unimapp.databinding.FragmentNotificationsBinding
import com.unimapp.unimapp.ui.main.notifications.activity.ActivityFragment
import com.unimapp.unimapp.ui.main.notifications.opportunity.OpportunityFragment
import com.unimapp.unimapp.ui.main.notifications.peers.PeersFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment :
    BaseFragment<NotificationsViewModel, FragmentNotificationsBinding, NotificationState, Unit>() {

    private val notificationViewPagerList by lazy {
        listOf(ActivityFragment(), PeersFragment(), OpportunityFragment())
    }
    private val adapter by lazy {
        NotificationsAdapter(parentFragmentManager, lifecycle, notificationViewPagerList)
    }

    private val tabLayoutNames by lazy {
        listOf(
            getString(R.string.notification_activity),
            getString(R.string.notification_peers),
            getString(R.string.notification_opportunity)
        )
    }

    override fun getViewModelClass() = NotificationsViewModel::class.java

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNotificationsBinding
        get() = FragmentNotificationsBinding::inflate

    override val onViewInit: FragmentNotificationsBinding.() -> Unit = {
        notificationViewPage.adapter = adapter
        TabLayoutMediator(
            notificationTabLayout,
            notificationViewPage,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = tabLayoutNames[position]
            }).attach()
    }
}