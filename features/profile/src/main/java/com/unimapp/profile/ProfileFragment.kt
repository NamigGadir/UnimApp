package com.unimapp.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.unimapp.common.extensions.*
import com.unimapp.core.BaseFragment
import com.unimapp.profile.databinding.FragmentProfileBinding
import com.unimapp.uitoolkit.tagview.TagItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding, ProfileState, Unit>() {

    companion object {
        const val PROFILE_USER_ID = "profile_user_id"
    }

    private val arguments by navArgs<ProfileFragmentArgs>()

    override fun getViewModelClass() = ProfileViewModel::class.java

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding
        get() = FragmentProfileBinding::inflate

    override val onViewInit: FragmentProfileBinding.() -> Unit = {
        initTags(viewmodel.getTagList())
        if (isOwnUserProfile()) {
            editProfileButton.show()
            addPeerButton.gone()
            editProfileButton.setOnClickListener {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment())
            }
        } else {
            editProfileButton.gone()
            addPeerButton.show()
            addPeerButton.setOnClickListener {
                addUserToPeer()
            }
        }
        profilePeers.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToPeersFragment(arguments.profileUserId ?: ""))
        }
    }

    private fun initTags(tagList: List<TagItem>) {
        withBinding {
            profileInterestTags.addTags(tagList)
        }
    }

    private fun isOwnUserProfile(): Boolean {
        return arguments.profileUserId.isNullOrEmpty()
    }

    private fun addUserToPeer() {

    }

}