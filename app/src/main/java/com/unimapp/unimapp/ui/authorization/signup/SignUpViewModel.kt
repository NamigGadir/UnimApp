package com.unimapp.unimapp.ui.authorization.signup

import com.unimapp.uitoolkit.dialogs.SimpleMultiSelectorBottomSheet
import com.unimapp.uitoolkit.dialogs.SimpleSingleSelectorBottomSheet
import com.unimapp.uitoolkit.tagview.TagItem
import com.unimapp.uitoolkit.tagview.tagItem
import com.unimapp.unimapp.R
import com.unimapp.unimapp.core.BaseViewModel

class SignUpViewModel : BaseViewModel<SignUpState>() {

    val interests by lazy { getInterestList() }
    val universities by lazy { getUniversitiesList() }
    val degrees by lazy { getDegreesList() }

    private val plusTag = tagItem {
        backgroundResurce = R.drawable.tagview_gray_background
        tagTextColor = R.color.white
        id = 1
        tagImage = R.drawable.ic_plus
    }

    private fun getInterestList() = arrayListOf(
        SimpleMultiSelectorBottomSheet.Item(1, "idman", false),
        SimpleMultiSelectorBottomSheet.Item(2, "web development", false),
        SimpleMultiSelectorBottomSheet.Item(3, "prosrammin", false),
        SimpleMultiSelectorBottomSheet.Item(4, "hiking", true),
        SimpleMultiSelectorBottomSheet.Item(5, "andorid", false),
        SimpleMultiSelectorBottomSheet.Item(6, "java", false)
    )

    private fun getUniversitiesList() = arrayListOf(
        SimpleSingleSelectorBottomSheet.Item(1, "Baki dovlet", false),
        SimpleSingleSelectorBottomSheet.Item(2, "Qafqaz", false),
        SimpleSingleSelectorBottomSheet.Item(3, "Ada", false),
        SimpleSingleSelectorBottomSheet.Item(4, "Iqtisad", true),
        SimpleSingleSelectorBottomSheet.Item(5, "AZI", false),
        SimpleSingleSelectorBottomSheet.Item(6, "API", false)
    )


    private fun getDegreesList() = arrayListOf(
        SimpleSingleSelectorBottomSheet.Item(1, "Bachelor", false),
        SimpleSingleSelectorBottomSheet.Item(2, "Master", true),
        SimpleSingleSelectorBottomSheet.Item(3, "Doctor", false),
    )

    fun getTagList(): List<TagItem> {
        return interests.filter { it.isSelected }.map {
            tagItem {
                id = it.itemId
                text = it.itemTitle
            }
        }.plus(plusTag)
    }

}

sealed class SignUpState {

}