package com.unimapp.authorization.signup

import com.ingress.core.BaseViewModel
import com.unimapp.authorization.R
import com.unimapp.domain.entities.auth.Interest
import com.unimapp.domain.usecases.GetInterestsUseCase
import com.unimapp.uitoolkit.dialogs.SimpleMultiSelectorBottomSheet
import com.unimapp.uitoolkit.dialogs.SimpleSingleSelectorBottomSheet
import com.unimapp.uitoolkit.tagview.TagItem
import com.unimapp.uitoolkit.tagview.tagItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val getInterestsUseCase: GetInterestsUseCase
) : BaseViewModel<SignUpState, Unit>() {

    var interests: List<SimpleMultiSelectorBottomSheet.Item> = arrayListOf()
    val universities by lazy { getUniversitiesList() }
    val degrees by lazy { getDegreesList() }

    private val plusTag = tagItem {
        backgroundResurce = R.drawable.tagview_gray_background
        tagTextColor = R.color.white
        id = 1
        tagImage = R.drawable.ic_plus
    }

    init {
        getInitialInfo()
    }

    private fun getInitialInfo() {
        invokeRequest(Unit, getInterestsUseCase) {
            postState(SignUpState.InitialValues(interestList = it))
        }
    }

    fun updateInterestsBottomSheet() {
        interests = getInterestListBottomSheet()
    }

    private fun getInterestList(): List<Interest> {
        if (state.value is SignUpState.InitialValues) {
            return (state.value as SignUpState.InitialValues).interestList
        }
        return arrayListOf()
    }

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

    private fun getInterestListBottomSheet(): List<SimpleMultiSelectorBottomSheet.Item> {
        return getInterestList().map {
            SimpleMultiSelectorBottomSheet.Item(it.id, it.name, false)
        }
    }

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
    data class InitialValues(val interestList: List<Interest>) : SignUpState()
}