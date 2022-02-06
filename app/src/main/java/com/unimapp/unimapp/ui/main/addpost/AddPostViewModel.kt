package com.unimapp.unimapp.ui.main.addpost

import androidx.lifecycle.viewModelScope
import com.unimapp.core.BaseViewModel
import com.unimapp.data.repository.linkretriever.LinkRetrieverRepository
import com.unimapp.domain.entities.linkretriever.LinkRetrieverResult
import com.unimapp.domain.validators.UrlValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor(
    private val linkRetrieverRepository: LinkRetrieverRepository,
    private val urlValidator: UrlValidator
) : BaseViewModel<AddPostState, Unit>() {

    var currentLink: String? = null

    fun getDataFromUrl(url: String) {
        if (currentLink != url)
            linkRetrieverRepository.retrieveLinkData(url)
                .onEach {
                    postState(AddPostState.LinkRetrieveModel(it))
                    currentLink = url
                }
                .flowOn(Dispatchers.IO)
                .launchIn(viewModelScope)
    }

    fun getIfTextContainsUrl(inputString: String): String? {
        return if (urlValidator.isValid(inputString)) {
            urlValidator.findUrl(inputString)
        } else null
    }

}

sealed class AddPostState {
    data class LinkRetrieveModel(val linkRetrieveResult: LinkRetrieverResult?) : AddPostState()
}


