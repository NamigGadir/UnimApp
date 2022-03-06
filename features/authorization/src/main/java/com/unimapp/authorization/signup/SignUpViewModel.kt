package com.unimapp.authorization.signup

import androidx.lifecycle.viewModelScope
import com.unimapp.authorization.R
import com.unimapp.core.BaseViewModel
import com.unimapp.domain.entities.auth.*
import com.unimapp.domain.usecases.auth.GetFacultiesUseCase
import com.unimapp.domain.usecases.auth.GetInterestsUseCase
import com.unimapp.domain.usecases.auth.GetUniversitiesUseCase
import com.unimapp.domain.validators.EmailValidator
import com.unimapp.uitoolkit.dialogs.SimpleMultiSelectorBottomSheet
import com.unimapp.uitoolkit.dialogs.SimpleSingleSelectorBottomSheet
import com.unimapp.uitoolkit.tagview.TagItem
import com.unimapp.uitoolkit.tagview.tagItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val getInterestsUseCase: GetInterestsUseCase,
    private val getUniversitiesUseCase: GetUniversitiesUseCase,
    private val getFacultiesUseCase: GetFacultiesUseCase,
    private val emailValidator: EmailValidator,
) : BaseViewModel<SignUpState, Unit>() {

    var interests: List<SimpleMultiSelectorBottomSheet.Item> = arrayListOf()
    var universities: List<SimpleSingleSelectorBottomSheet.Item> = arrayListOf()
    var faculties: List<SimpleSingleSelectorBottomSheet.Item> = arrayListOf()
    val degrees by lazy { getDegreesList() }
    val years by lazy { getYearsList() }

    private val plusTag = tagItem {
        backgroundResurce = R.drawable.tagview_gray_background
        tagTextColor = R.color.white
        id = 1
        tagImage = R.drawable.ic_plus
    }

    init {
        launchAll()
    }

    private fun launchAll() {
        viewModelScope.launch {
            var interestRemote: List<Interest> = arrayListOf()
            var universitiesRemote: List<University> = arrayListOf()
            var facultiesRemote: List<Faculty> = arrayListOf()
            listOf(
                async(handler) {
                    interestRemote = getInterestsUseCase.call(Unit).content
                },
                async(handler) {
                    universitiesRemote = getUniversitiesUseCase.call(Unit).content
                },
                async(handler) {
                    facultiesRemote = getFacultiesUseCase.call(Unit).content
                }).map {
                it.await()
            }
            postState(SignUpState.InitialValues(interestList = interestRemote, universities = universitiesRemote, faculties = facultiesRemote))
        }
    }

    fun updateInitialValues() {
        interests = getInterestListBottomSheet()
        universities = getUniversitiesListBottomSheet()
        faculties = getFacultiesListBottomSheet()
    }

    private fun getInterestList(): List<Interest> {
        if (state.value is SignUpState.InitialValues) {
            return (state.value as SignUpState.InitialValues).interestList
        }
        return arrayListOf()
    }

    private fun getUniversitiesList(): List<University> {
        if (state.value is SignUpState.InitialValues) {
            return (state.value as SignUpState.InitialValues).universities
        }
        return arrayListOf()
    }

    private fun getFacultiesList(): List<Faculty> {
        if (state.value is SignUpState.InitialValues) {
            return (state.value as SignUpState.InitialValues).faculties
        }
        return arrayListOf()
    }

    private fun getDegreesList() =
        DegreeTypes.values().map {
            SimpleSingleSelectorBottomSheet.Item(it.index, it.type, false)
        }

    private fun getYearsList() = (2022 downTo 1970).map {
        SimpleSingleSelectorBottomSheet.Item(it, it.toString(), false)
    }


    private fun getInterestListBottomSheet(): List<SimpleMultiSelectorBottomSheet.Item> {
        return getInterestList().map {
            SimpleMultiSelectorBottomSheet.Item(it.id, it.name, false)
        }
    }

    private fun getUniversitiesListBottomSheet(): List<SimpleSingleSelectorBottomSheet.Item> {
        return getUniversitiesList().map {
            SimpleSingleSelectorBottomSheet.Item(it.id, it.name, false)
        }
    }

    private fun getFacultiesListBottomSheet(): List<SimpleSingleSelectorBottomSheet.Item> {
        return getFacultiesList().map {
            SimpleSingleSelectorBottomSheet.Item(it.id, it.name, false)
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

    fun isEmailValid(text: String): Boolean {
        return emailValidator.isValid(text)
    }

    fun getSelectedDegreeType(selectedDegree: SimpleSingleSelectorBottomSheet.Item?): String? {
        return DegreeTypes.findById(selectedDegree?.itemId)?.type
    }

}

sealed class SignUpState {
    data class InitialValues(val interestList: List<Interest>, val universities: List<University>, val faculties: List<Faculty>) : SignUpState()
}