package com.unimapp.unimapp.ui.main.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.ingress.core.BaseFragment
import com.unimapp.common.extensions.asColorResource
import com.unimapp.domain.entities.profile.About
import com.unimapp.uitoolkit.adapters.SimpleViewpagerAdapter
import com.unimapp.uitoolkit.databinding.TablayoutCustomTapBinding
import com.unimapp.uitoolkit.tagview.TagItem
import com.unimapp.unimapp.R
import com.unimapp.unimapp.databinding.FragmentProfileBinding
import com.unimapp.unimapp.ui.main.profile.about.AboutFragment
import com.unimapp.unimapp.ui.main.profile.activity.ProfileActivityFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment :
    BaseFragment<ProfileViewModel, FragmentProfileBinding, ProfileState, Unit>(), ProfileAdapter.ProfileActionListener {

    private val fragments by lazy {
        listOf(AboutFragment(), ProfileActivityFragment())
    }

    private val tabItems by lazy {
        listOf(
            ProfileTabItem(
                getString(R.string.profile_about),
                R.color.stroke_color
            ),
            ProfileTabItem(
                getString(R.string.profile_activity),
                R.color.stroke_color
            )
        )
    }

    override fun getViewModelClass() = ProfileViewModel::class.java

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding
        get() = FragmentProfileBinding::inflate

    override val onViewInit: FragmentProfileBinding.() -> Unit = {
        initTags(viewmodel.getTagList())
        for (i in fragments.indices) {
            val tab: TabLayout.Tab = profileTabLayout.newTab()
            tab.customView = getTabView(i)
            if (i == 0) {
                profileTabLayout.addTab(tab, true) //Default first callback
            } else {
                profileTabLayout.addTab(tab)
            }
            binding.profileViewPage.adapter =
                SimpleViewpagerAdapter(fragments, childFragmentManager, lifecycle)
            profileViewPage.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    profileTabLayout.selectTab(profileTabLayout.getTabAt(position))
                }
            })
            profileTabLayout.addOnTabSelectedListener(object :
                TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    profileViewPage.currentItem = tab.position
                    val tv =
                        tab.view.findViewById<View>(com.unimapp.uitoolkit.R.id.tab_title) as TextView
                    tv.setTextColor(tabItems[tab.position].textColor.asColorResource(requireContext()))
                    profileTabLayout.setSelectedTabIndicatorColor(
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
                tabTitle.text = it.title
            }
            this.root
        }
    }

    private fun initTags(tagList: List<TagItem>) {
        withBinding {
            profileInterestTags.addTags(tagList)
        }
    }

    class ProfileTabItem(
        val title: String,
        val textColor: Int,
    )

}