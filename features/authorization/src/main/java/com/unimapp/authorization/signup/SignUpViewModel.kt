package com.unimapp.authorization.signup

import androidx.lifecycle.viewModelScope
import com.ingress.core.BaseViewModel
import com.unimapp.authorization.R
import com.unimapp.domain.base.RemoteResponse
import com.unimapp.domain.entities.auth.Faculty
import com.unimapp.domain.entities.auth.Interest
import com.unimapp.domain.entities.auth.University
import com.unimapp.domain.usecases.GetFacultiesUseCase
import com.unimapp.domain.usecases.GetInterestsUseCase
import com.unimapp.domain.usecases.GetUniversitiesUseCase
import com.unimapp.uitoolkit.dialogs.SimpleMultiSelectorBottomSheet
import com.unimapp.uitoolkit.dialogs.SimpleSingleSelectorBottomSheet
import com.unimapp.uitoolkit.tagview.TagItem
import com.unimapp.uitoolkit.tagview.tagItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val getInterestsUseCase: GetInterestsUseCase,
    private val getUniversitiesUseCase: GetUniversitiesUseCase,
    private val getFacultiesUseCase: GetFacultiesUseCase
) : BaseViewModel<SignUpState, Unit>() {

    var interests: List<SimpleMultiSelectorBottomSheet.Item> = arrayListOf()
    var universities: List<SimpleSingleSelectorBottomSheet.Item> = arrayListOf()
    var faculties: List<SimpleSingleSelectorBottomSheet.Item> = arrayListOf()
    val degrees by lazy { getDegreesList() }

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
                    interestRemote = getInterestsUseCase.call(Unit).result.content
                },
                async(handler) {
                    universitiesRemote = getUniversitiesUseCase.call(Unit).result.content
                },
                async(handler) {
                    facultiesRemote = getFacultiesUseCase.call(Unit).result.content
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
}


sealed class SignUpState {
    data class InitialValues(val interestList: List<Interest>, val universities: List<University>, val faculties: List<Faculty>) : SignUpState()
}