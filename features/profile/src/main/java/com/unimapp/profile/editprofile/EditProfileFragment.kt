package com.unimapp.profile.editprofile

import android.view.LayoutInflater
import android.view.ViewGroup
import com.unimapp.common.extensions.toast
import com.unimapp.core.BaseFragment
import com.unimapp.profile.databinding.FragmentEditProfileBinding

class EditProfileFragment : BaseFragment<EditProfileViewModel, FragmentEditProfileBinding, Unit, Unit>() {

    override fun getViewModelClass() = EditProfileViewModel::class.java

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEditProfileBinding
        get() = FragmentEditProfileBinding::inflate

    override val onViewInit: FragmentEditProfileBinding.() -> Unit = {
        addProfileCover.setOnClickListener {
            getProfileCoverImage()
        }
        addProfileImage.setOnClickListener {
            getProfileImage()
        }
    }

    private fun getProfileImage() {
        toast("Profile")
    }

    private fun getProfileCoverImage() {
        toast("Cover")
    }
}