package com.unimapp.unimapp.ui.main.profile.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ingress.core.BaseFragment
import com.unimapp.domain.entities.profile.About
import com.unimapp.domain.entities.profile.AboutType
import com.unimapp.uitoolkit.extensions.addDivider
import com.unimapp.unimapp.databinding.FragmentAboutBinding
import com.unimapp.unimapp.ui.main.profile.ProfileAdapter
import com.unimapp.unimapp.ui.main.profile.ProfileState
import com.unimapp.unimapp.ui.main.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutFragment : BaseFragment<ProfileViewModel, FragmentAboutBinding, ProfileState, Unit>() {

    private val adapter by lazy { ProfileAdapter(viewmodel.getAbout()) }
    override fun getViewModelClass() = ProfileViewModel::class.java
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

    override fun onStateUpdate(state: ProfileState) {
        when (state) {
            is ProfileState.AboutList -> {
                showAbout(state.abouts)
            }
        }
    }

    private fun showAbout(about: List<About>) {
        //adapter.submitList(about)
    }
}