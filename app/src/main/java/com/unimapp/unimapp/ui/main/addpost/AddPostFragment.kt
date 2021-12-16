package com.unimapp.unimapp.ui.main.addpost

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.unimapp.common.extensions.*
import com.unimapp.unimapp.BuildConfig
import com.unimapp.unimapp.core.BaseFragment
import com.unimapp.unimapp.databinding.FragmentAddPostBinding
import java.io.File

import com.unimapp.domain.entities.linkretriever.LinkRetrieverResult
import com.unimapp.uitoolkit.resource_view.FileResourceView
import com.unimapp.uitoolkit.resource_view.LinkResourceView
import com.unimapp.unimapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class AddPostFragment : BaseFragment<AddPostViewModel, FragmentAddPostBinding, AddPostState, Unit>(), View.OnClickListener {

    private var imageUri: Uri? = null
    private val takeImageResults = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            imageUri?.let {
                binding.gallery.setImageURI(it)
            }
        }
    }

    private val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { binding.gallery.setImageURI(uri) }
    }

    private val selectDocumentResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {

        }
    }

    override fun getViewModelClass() = AddPostViewModel::class.java

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddPostBinding
        get() = FragmentAddPostBinding::inflate

    override val onViewInit: FragmentAddPostBinding.() -> Unit = {
        camera.setOnClickListener(this@AddPostFragment)
        gallery.setOnClickListener(this@AddPostFragment)
        document.setOnClickListener(this@AddPostFragment)
        tagFriend.setOnClickListener(this@AddPostFragment)
        setResourceBackground()
        status.afterTextChangedDebounce(2000) { text ->
            checkTextForHyperlink(text)
        }
    }

    private fun checkTextForHyperlink(text: String) {
        viewmodel.getIfTextContainsUrl(text)?.let {
            viewmodel.getDataFromUrl(it)
        }
    }

    private fun setResourceBackground() {
        binding.singleDocumentView.addBorder(R.color.white, R.color.stroke_color, 16.dp.toFloat(), 1)
        binding.singleLinkView.addBorder(R.color.white, R.color.stroke_color, 16.dp.toFloat(), 1)
        binding.singleDocumentView.setDocumentInfo(FileResourceView.DocumentInfo("Nagillear.pdf", "4.5 MB", FileResourceView.DocumentType.PDF))
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.camera -> takeImage()
            binding.gallery -> selectImageFromGallery()
            binding.document -> selectFromDocument()
            binding.tagFriend -> tagFriends()
        }
    }

    private fun takeImage() {
        lifecycleScope.launchWhenStarted {
            getTmpFileUri().let { uri ->
                imageUri = uri
                takeImageResults.launch(uri)
            }
        }
    }

    private fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")
    private fun selectFromDocument() = selectDocumentResult.launch("*/*")

    private fun tagFriends() {
        findNavController().navigate(R.id.tagFriendFragment)
    }

    private fun getTmpFileUri(): Uri {
        val tmpFile = File.createTempFile("unim_temp_file", ".png", requireActivity().cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }
        return FileProvider.getUriForFile(requireContext(), "${BuildConfig.APPLICATION_ID}.provider", tmpFile)
    }

    override fun onStateUpdate(state: AddPostState) {
        when (state) {
            is AddPostState.LinkRetrieveModel -> {
                showLinkRetriever(state.linkRetrieveResult)
            }
        }
    }

    private fun showLinkRetriever(linkRetrieveResult: LinkRetrieverResult?) {
        withBinding {
            linkRetrieveResult?.let {
                if (linkRetrieveResult.url == null && linkRetrieveResult.title == null) {
                    singleLinkView.gone()
                } else {
                    singleLinkView.show()
                    singleLinkView.setLinkInfo(LinkResourceView.LinkInfo(linkRetrieveResult.title, linkRetrieveResult.url, linkRetrieveResult.image))
                }
            } ?: run {
                singleLinkView.gone()
            }
        }
    }
}