package com.unimapp.unimapp.ui.main.profile

import com.unimapp.uitoolkit.dialogs.SimpleMultiSelectorBottomSheet
import com.unimapp.uitoolkit.tagview.TagItem
import com.unimapp.uitoolkit.tagview.tagItem
import com.unimapp.unimapp.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : BaseViewModel<ProfileState, Unit>() {

    val interests by lazy { getInterestList() }

    private fun getInterestList() = arrayListOf(
        SimpleMultiSelectorBottomSheet.Item(1, "Art", false),
        SimpleMultiSelectorBottomSheet.Item(2, "Web Development", false),
        SimpleMultiSelectorBottomSheet.Item(3, "Graphic Design", false),
        SimpleMultiSelectorBottomSheet.Item(4, "Music", true),
        SimpleMultiSelectorBottomSheet.Item(5, "Startups", false),
        SimpleMultiSelectorBottomSheet.Item(6, "Fashion", false),
        SimpleMultiSelectorBottomSheet.Item(7, "Volleyball", false)
    )

    fun getTagList(): List<TagItem> {
        return interests.map {
            tagItem {
                id = it.itemId
                text = it.itemTitle
            }
        }
    }
}


sealed class ProfileState{

}