package com.unimapp.unimapp.ui.main.profile.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.unimapp.core.BaseFragment
import com.unimapp.domain.entities.profile.About
import com.unimapp.domain.entities.profile.AboutType
import com.unimapp.unimapp.databinding.FragmentAboutBinding
import com.unimapp.unimapp.ui.main.profile.ProfileAdapter
import com.unimapp.profile.ProfileState
import com.unimapp.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutFragment : BaseFragment<com.unimapp.profile.ProfileViewModel, FragmentAboutBinding, com.unimapp.profile.ProfileState, Unit>() {

    private val adapter by lazy { ProfileAdapter(viewmodel.getAbout()) }
    override fun getViewModelClass() = com.unimapp.profile.ProfileViewModel::class.java
    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAboutBinding
        get() = FragmentAboutBinding::inflate

    companion object {
        private const val ABOUT_TYPE = "about_type"
        private const val ABOUT_ID = "about_id"

        fun newInstance(aboutType: AboutType, aboutId: Long): AboutFragment {
            val args = Bundle()
            args.putString(ABOUT_TYPE, aboutType.name)
            args.putLong(ABOUT_ID, aboutId)
            val fragment = AboutFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override val onViewInit: FragmentAboutBinding.() -> Unit = {
        aboutRecyclerView.adapter = adapter
    }

    override fun onStateUpdate(state: com.unimapp.profile.ProfileState) {
        when (state) {
            is com.unimapp.profile.ProfileState.AboutList -> {
                showAbout(state.abouts)
            }
        }
    }

    private fun showAbout(about: List<About>) {
        //adapter.submitList(about)
    }
}