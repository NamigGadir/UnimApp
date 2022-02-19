package com.unimapp.home.addpost

import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.unimapp.core.BaseViewModel
import com.unimapp.data.repository.linkretriever.LinkRetrieverRepository
import com.unimapp.data.util.RandomStringGenerator
import com.unimapp.domain.base.RemoteResponse
import com.unimapp.domain.entities.feed.CreatePostRequest
import com.unimapp.domain.entities.linkretriever.LinkRetrieverResult
import com.unimapp.domain.usecases.onboarding.UploadImageUseCase
import com.unimapp.domain.validators.UrlValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor(
    private val linkRetrieverRepository: LinkRetrieverRepository,
    private val urlValidator: UrlValidator,
    private val uploadImageUseCase: UploadImageUseCase,
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

    fun sendPost(requestBody: CreatePostRequest, selectedUriList: List<File>) {
        if (selectedUriList.isNotEmpty()) {
            launch {
                selectedUriList.map {
                    async {
                        uploadImageUseCase.invoke(UploadImageUseCase.Params(it))
                    }
                }.map {
                    it.await()
                }.map {
                    if (it is RemoteResponse.Success)
                        it.result
                }
            }
        }
    }

    fun getFilesFromUri(context: Context, list: ArrayList<Uri>): List<File> {
        return list.map {
            getFileFromUri(context, it)
        }
    }

    fun getFileFromUri(context: Context, uri: Uri): File {
        return context.contentResolver.openInputStream(uri).use { stream ->
            File(context.cacheDir, "${RandomStringGenerator.getRandomString()}.jpg").also { file ->
                file.outputStream().use { output ->
                    stream?.copyTo(output)
                }
            }
        }
    }


}

sealed class AddPostState {
    data class LinkRetrieveModel(val linkRetrieveResult: LinkRetrieverResult?) : AddPostState()
}


