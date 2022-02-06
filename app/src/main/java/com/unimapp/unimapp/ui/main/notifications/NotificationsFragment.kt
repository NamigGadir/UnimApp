package com.unimapp.unimapp.ui.main.notifications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.unimapp.common.extensions.asColorResource
import com.unimapp.common.extensions.gone
import com.unimapp.uitoolkit.adapters.SimpleViewpagerAdapter
import com.unimapp.uitoolkit.databinding.TablayoutCustomTapBinding
import com.unimapp.unimapp.R
import com.ingress.core.BaseFragment
import com.unimapp.unimapp.databinding.FragmentNotificationsBinding
import com.unimapp.unimapp.ui.main.notifications.activity.ActivityFragment
import com.unimapp.unimapp.ui.main.notifications.opportunity.OpportunityFragment
import com.unimapp.unimapp.ui.main.notifications.peers.PeersFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment :
    com.ingress.core.BaseFragment<NotificationsViewModel, FragmentNotificationsBinding, NotificationState, Unit>() {

    private val fragments by lazy {
        listOf(ActivityFragment(), PeersFragment(), OpportunityFragment())
    }

    private val tabItems by lazy {
        listOf(
            NotificationTabItem(12, getString(R.string.notification_activity), R.color.stroke_color),
            NotificationTabItem(15, getString(R.string.notification_peers), R.color.stroke_color),
            NotificationTabItem(22, getString(R.string.notification_opportunity), R.color.stroke_color)
        )
    }

    override fun getViewModelClass() = NotificationsViewModel::class.java

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNotificationsBinding
        get() = FragmentNotificationsBinding::inflate

    override val onViewInit: FragmentNotificationsBinding.() -> Unit = {
        for (i in fragments.indices) {
            val tab: TabLayout.Tab = notificationTabLayout.newTab()
            tab.customView = getTabView(i)
            if (i == 0) {
                notificationTabLayout.addTab(tab, true) //Default first callback
            } else {
                notificationTabLayout.addTab(tab)
            }
            binding.notificationViewPage.adapter =
                SimpleViewpagerAdapter(fragments, childFragmentManager, lifecycle)
            notificationViewPage.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    notificationTabLayout.selectTab(notificationTabLayout.getTabAt(position))
                }
            })
            notificationTabLayout.addOnTabSelectedListener(object :
                TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    notificationViewPage.currentItem = tab.position
                    val tv =
                        tab.view.findViewById<View>(com.unimapp.uitoolkit.R.id.tab_title) as TextView
                    tv.setTextColor(tabItems[tab.position].textColor.asColorResource(requireContext()))
                    notificationTabLayout.setSelectedTabIndicatorColor(
                        tabItems[tab.position].textColor.asColorResource(
                            requireContext()
                        )
                    )
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    val tv =
                        tab.view.findViewById<View>(com.unimapp.uitoolkit.R.id.tab_title) as TextView
                    tv.setTextColor(
                        R.color.tab_unselected_color.asColorResource(requireContext())
                    )
                }

                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        }
    }

    private fun getTabView(position: Int): View {
        return with(TablayoutCustomTapBinding.inflate(LayoutInflater.from(context))) {
            tabItems.getOrNull(position)?.let {
                if (it.count > 0) tabCount.setText(it.count.toString())
                else tabCount.gone()
                tabTitle.text = it.title
            }
            this.root
        }
    }

    class NotificationTabItem(
        val count: Int,
        val title: String,
        val textColor: Int,
    )
}