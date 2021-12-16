package com.unimapp.unimapp.ui.main.tagfriend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.unimapp.uitoolkit.R
import com.unimapp.uitoolkit.databinding.SimpleSelectorItemBinding
import com.unimapp.uitoolkit.databinding.TagFriendListItemBinding
import com.unimapp.uitoolkit.dialogs.SimpleMultiSelectorBottomSheet
import com.unimapp.uitoolkit.extensions.addDivider
import com.unimapp.uitoolkit.extensions.withSingleAdapter
import com.unimapp.unimapp.core.BaseFragment
import com.unimapp.unimapp.databinding.FragmentHomePageBinding
import com.unimapp.unimapp.databinding.FragmentTagFriendBinding

class TagFriendFragment : BaseFragment<TagFriendViewModel, FragmentTagFriendBinding, Unit, Unit>() {

    override fun getViewModelClass() = TagFriendViewModel::class.java

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTagFriendBinding
        get() = FragmentTagFriendBinding::inflate


    override val onViewInit: FragmentTagFriendBinding.() -> Unit = {
        taggedPeersList.withSingleAdapter(
            getInterestList(),
            bindingCallback = { viewGroup: ViewGroup?, attachToParent: Boolean ->
                TagFriendListItemBinding.inflate(LayoutInflater.from(context), viewGroup, attachToParent)
            }, onBindView = { data ->
                checkBox.text = data.itemTitle
                checkBox.isChecked = data.isSelected
                checkBox.setOnCheckedChangeListener { compoundButton, checked ->
                    data.isSelected = checked
                }
            }, onItemClick = {

            }).addDivider(height = 1, color = ContextCompat.getColor(requireContext(), R.color.dark_gray))

        recentsList.withSingleAdapter(
            getInterestList(),
            bindingCallback = { viewGroup: ViewGroup?, attachToParent: Boolean ->
                TagFriendListItemBinding.inflate(LayoutInflater.from(context), viewGroup, attachToParent)
            }, onBindView = { data ->
                checkBox.text = data.itemTitle
                checkBox.isChecked = data.isSelected
                checkBox.setOnCheckedChangeListener { compoundButton, checked ->
                    data.isSelected = checked
                }
            }, onItemClick = {

            }).addDivider(height = 1, color = ContextCompat.getColor(requireContext(), R.color.dark_gray))

        peersList.withSingleAdapter(
            getInterestList(),
            bindingCallback = { viewGroup: ViewGroup?, attachToParent: Boolean ->
                TagFriendListItemBinding.inflate(LayoutInflater.from(context), viewGroup, attachToParent)
            }, onBindView = { data ->
                checkBox.text = data.itemTitle
                checkBox.isChecked = data.isSelected
                checkBox.setOnCheckedChangeListener { compoundButton, checked ->
                    data.isSelected = checked
                }
            }, onItemClick = {

            }).addDivider(height = 1, color = ContextCompat.getColor(requireContext(), R.color.dark_gray))

    }


    private fun getInterestList() = arrayListOf(
        SimpleMultiSelectorBottomSheet.Item(1, "idman", false),
        SimpleMultiSelectorBottomSheet.Item(2, "web development", false),
        SimpleMultiSelectorBottomSheet.Item(3, "prosrammin", false),
        SimpleMultiSelectorBottomSheet.Item(4, "hiking", true),
        SimpleMultiSelectorBottomSheet.Item(5, "andorid", false),
        SimpleMultiSelectorBottomSheet.Item(6, "java", false)
    )
}