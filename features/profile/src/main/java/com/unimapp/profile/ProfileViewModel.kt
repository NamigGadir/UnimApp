package com.unimapp.profile

import com.unimapp.core.BaseViewModel
import com.unimapp.domain.entities.profile.About
import com.unimapp.domain.entities.profile.AboutType
import com.unimapp.uitoolkit.dialogs.SimpleMultiSelectorBottomSheet
import com.unimapp.uitoolkit.tagview.TagItem
import com.unimapp.uitoolkit.tagview.tagItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : BaseViewModel<ProfileState, Unit>() {

    private val interests by lazy { getInterestList() }

    private val abouts by lazy { getAboutList() }

    private fun getAboutList() = arrayListOf(
        About(1, "Worked as Software Engineer", AboutType.WORK),
        About(2, "Worked as Architect Engineer", AboutType.WORK)
    )

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

    fun getAbout(): List<About> {
        return abouts.map { about ->
            About(
                aboutId = about.aboutId,
                aboutTitle = about.aboutTitle,
                aboutType = about.aboutType
            )
        }
    }
}


sealed class ProfileState {
    class AboutList(val abouts: List<About>) : ProfileState()
}