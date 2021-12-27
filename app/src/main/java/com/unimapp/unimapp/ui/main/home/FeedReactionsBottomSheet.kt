package com.unimapp.unimapp.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.unimapp.common.extensions.asColorResource
import com.unimapp.common.extensions.gone
import com.unimapp.domain.entities.feed.FeedReactionType
import com.unimapp.uitoolkit.R
import com.unimapp.uitoolkit.adapters.SimpleViewpagerAdapter
import com.unimapp.uitoolkit.databinding.TablayoutCustomTapBinding
import com.unimapp.uitoolkit.dialogs.BaseBottomSheetDialog
import com.unimapp.unimapp.databinding.BottomSheetFeedReactionsBinding


class FeedReactionsBottomSheet() : BaseBottomSheetDialog() {

    private lateinit var binding: BottomSheetFeedReactionsBinding
    private val tabItems by lazy {
        listOf(
            TabItem(getString(R.string.all_reaction_count, 23), R.color.unim_main_color, 0),
            TabItem("12", R.color.unim_main_color, R.drawable.ic_reaction_star),
            TabItem("8", R.color.unim_active_color, R.drawable.ic_reaction_haha),
            TabItem("3", R.color.unim_wrong, R.drawable.ic_reaction_love)
        )
    }

    private val fragments by lazy {
        listOf(
            FeedReactionFragment.newInstance(FeedReactionType.NOT_SPECIFIED, 1),
            FeedReactionFragment.newInstance(FeedReactionType.STAR, 1),
            FeedReactionFragment.newInstance(FeedReactionType.ANGRY, 1),
            FeedReactionFragment.newInstance(FeedReactionType.LOVE, 1),
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BottomSheetFeedReactionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        with(binding) {
            for (i in fragments.indices) {
                val tab: TabLayout.Tab = tabLayout.newTab()
                tab.customView = getTabView(i)
                if (i == 0) {
                    tabLayout.addTab(tab, true) //Default first callback
                } else {
                    tabLayout.addTab(tab)
                }
            }
            binding.reactionsContainer.adapter = SimpleViewpagerAdapter(fragments, childFragmentManager, lifecycle)
            reactionsContainer.registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    tabLayout.selectTab(tabLayout.getTabAt(position))
                }
            })
            tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    reactionsContainer.currentItem = tab.position
                    val tv = tab.view.findViewById<View>(R.id.tab_title) as TextView
                    tv.setTextColor(tabItems[tab.position].textColor.asColorResource(requireContext()))
                    tabLayout.setSelectedTabIndicatorColor(tabItems[tab.position].textColor.asColorResource(requireContext()))
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    val tv = tab.view.findViewById<View>(R.id.tab_title) as TextView
                    tv.setTextColor(R.color.unim_message_color.asColorResource(requireContext()))
                }

                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        }
    }

    private fun getTabView(position: Int): View {
        return with(TablayoutCustomTapBinding.inflate(LayoutInflater.from(context))) {
            tabItems.getOrNull(position)?.let {
                if (it.tabIcon > 0) tabImage.setImageResource(it.tabIcon)
                else tabImage.gone()
                tabTitle.text = it.title
            }
            this.root
        }
    }

    class Builder {

        fun build() = FeedReactionsBottomSheet(

        )
    }

    class TabItem(
        val title: String,
        val textColor: Int,
        val tabIcon: Int
    )
}

fun feedReactionsBottomSheet(lambda: FeedReactionsBottomSheet.Builder.() -> Unit) =
    FeedReactionsBottomSheet.Builder().apply(lambda).build()
