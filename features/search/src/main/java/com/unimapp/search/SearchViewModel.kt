package com.unimapp.search

import androidx.lifecycle.viewModelScope
import com.unimapp.core.BaseViewModel
import com.unimapp.uitoolkit.adapters.UserInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel : BaseViewModel<SearchState, Unit>() {

    fun getSearchList(searchText: String) {
        viewModelScope.launch {
            handleProgressBar.postValue(true)
            delay(1000)
            handleProgressBar.postValue(false)
            val list = list
            updateList(searchText, list.filter { it.userName.contains(searchText, ignoreCase = true) })
        }
    }

    private fun updateList(searchQuote: String, list: List<UserInfo>) {
        postState(SearchState.OnSearchResult(searchQuote, list))
    }

    private val list = listOf(
        UserInfo(
            "123","Namig Qadirov", "https://upload.wikimedia.org/wikipedia/commons/e/eb/Jeff_Bell_profile_photo.jpg", "Aytisnik", "Qafqaz University", "Nesimi bazari"
        ),
        UserInfo(
            "1234","Namig Qadirov", "https://upload.wikimedia.org/wikipedia/commons/e/eb/Jeff_Bell_profile_photo.jpg", "Aytisnik", "Qafqaz University", "Nesimi bazari"
        ),
        UserInfo(
            "1235","Idrak Atakisili", "https://upload.wikimedia.org/wikipedia/commons/e/eb/Jeff_Bell_profile_photo.jpg", "Aytisnik", "Qafqaz University", "Nesimi bazari"
        ),
        UserInfo(
            "1236","Shahmal Quliyev", "https://upload.wikimedia.org/wikipedia/commons/e/eb/Jeff_Bell_profile_photo.jpg", "Aytisnik", "Qafqaz University", "Nesimi bazari"
        ),
        UserInfo(
            "1237","Namig Qadirov", "https://upload.wikimedia.org/wikipedia/commons/e/eb/Jeff_Bell_profile_photo.jpg", "Aytisnik", "Qafqaz University", "Nesimi bazari"
        ),
        UserInfo(
            "1238","Namig Qadirov", "https://upload.wikimedia.org/wikipedia/commons/e/eb/Jeff_Bell_profile_photo.jpg", "Aytisnik", "Qafqaz University", "Nesimi bazari"
        )
    )
}

sealed class SearchState {
    data class OnSearchResult(val searchQuote: String, val list: List<UserInfo>) : SearchState()
}