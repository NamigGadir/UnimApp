package com.unimapp.home.addpost

import android.net.Uri
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.unimapp.common.extensions.*
import com.unimapp.core.BaseFragment
import com.unimapp.domain.entities.feed.CreatePostRequest
import com.unimapp.domain.entities.feed.PostCreateType
import com.unimapp.domain.entities.linkretriever.LinkRetrieverResult
import com.unimapp.home.R
import com.unimapp.home.databinding.FragmentAddPostBinding
import com.unimapp.uitoolkit.input.CutCopyPasteEditText
import com.unimapp.uitoolkit.resource_view.FileResourceView
import com.unimapp.uitoolkit.resource_view.LinkResourceView
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.DecimalFormat
import kotlin.math.log10
import kotlin.math.pow


@AndroidEntryPoint
class AddPostFragment : BaseFragment<AddPostViewModel, FragmentAddPostBinding, AddPostState, Unit>(), View.OnClickListener {

    private var imageUri: Uri? = null
    private val selectedUriList: ArrayList<Uri> = arrayListOf()
    private var selectedDocument: Uri? = null
    private val adapter by lazy {
        ImagesListAdapter()

    }
    private val takeImageResults = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            imageUri?.let {
                selectedUriList.add(it)
                showSelectedImage()
            }
        }
    }

    private val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris: List<Uri>? ->
        uris?.let {
            val list = arrayListOf<Uri>()
            val currentListSize = selectedUriList.size
            val selectedListSize = it.size
            if (currentListSize + selectedListSize > 4) {
                list.addAll(it.subList(0, 4 - (currentListSize)))
                showMaxImageSelectError()
            } else
                list.addAll(it)
            selectedUriList.addAll(list)
            uris.forEach { _ ->
                showSelectedImage()
            }
        }
    }

    private val selectDocumentResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            showSelectedDocument(it)
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
        postButton.setOnClickListener(this@AddPostFragment)
        setResourceBackground()
        status.setOnCutCopyPasteListener(object : CutCopyPasteEditText.OnCutCopyPasteListener {
            override fun onCut() {
                //Nothing happened
            }

            override fun onCopy() {
                //Nothing happened
            }

            override fun onPaste() {
                checkTextForHyperlink(status.text.toString())
            }
        })
        singleLinkView.setOnDeleteClicked {
            clearLinkView()
        }
        binding.singleImageList.adapter = adapter
    }

    private fun checkTextForHyperlink(text: String) {
        viewmodel.getIfTextContainsUrl(text)?.let {
            viewmodel.getDataFromUrl(it)
        }
    }

    private fun setResourceBackground() {
        binding.singleDocumentView.addBorder(R.color.white, R.color.stroke_color, 16.dp.toFloat(), 1)
        binding.singleLinkView.addBorder(R.color.white, R.color.stroke_color, 16.dp.toFloat(), 1)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.camera -> takeImage()
            binding.gallery -> selectImageFromGallery()
            binding.document -> selectFromDocument()
            binding.tagFriend -> tagFriends()
            binding.postButton -> sendPost()
        }
    }

    private fun sendPost() {
        withBinding {
            val requestBody = CreatePostRequest(
                caption = status.text.toString(),
                postType = PostCreateType.OTHER.name
            )
            val uploadList = selectedDocument?.let {
                viewmodel.getFilesFromUri(requireContext(), selectedUriList) + viewmodel.getFileFromUri(requireContext(), it)
            } ?: viewmodel.getFilesFromUri(requireContext(), selectedUriList)
            viewmodel.sendPost(requestBody, uploadList)
        }
    }

    private fun takeImage() {
        if (selectedUriList.size <= 4)
            lifecycleScope.launchWhenStarted {
                getTmpFileUri().let { uri ->
                    imageUri = uri
                    takeImageResults.launch(uri)
                }
            }
        else
            showMaxImageSelectError()
    }

    private fun selectImageFromGallery() {
        if (selectedUriList.size <= 4)
            selectImageFromGalleryResult.launch("image/*")
        else
            showMaxImageSelectError()
    }

    private fun selectFromDocument() = selectDocumentResult.launch("*/*")

    private fun tagFriends() {
        findNavController().navigate(R.id.tagFriendFragment)
    }

    private fun getTmpFileUri(): Uri {
        val tmpFile = File.createTempFile("unim_temp_file", ".png", requireActivity().cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }
        return FileProvider.getUriForFile(requireContext(), "${requireContext().packageName}.provider", tmpFile)
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

    private fun showSelectedImage() {
        binding.singleImageList.show()
        adapter.submitList(selectedUriList.toMutableList())
        clearLinkView()
        clearDocumentView()
    }

    private fun showSelectedDocument(docUri: Uri) {
        selectedDocument = docUri
        requireContext().contentResolver.query(docUri, null, null, null, null)
            ?.use { cursor ->
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                cursor.moveToFirst()
                val fileName = cursor.getString(nameIndex)
                val fileSize = cursor.getLong(sizeIndex)
                binding.singleDocumentView.setDocumentInfo(
                    FileResourceView.DocumentInfo(fileName, readableFileSize(fileSize), FileResourceView.DocumentType.PDF)
                )
                binding.singleDocumentView.show()
            }
        clearLinkView()
        clearImageView()
    }

    private fun clearImageView() {
        binding.singleImageList.gone()
        selectedUriList.clear()
    }

    private fun clearDocumentView() {
        binding.singleDocumentView.gone()
        selectedDocument = null
    }

    private fun clearLinkView() {
        binding.singleLinkView.gone()
    }

    private fun showMaxImageSelectError() {
        toast("Max 4 image selection allowed")
    }

    private fun isHypeLinkVisible() = binding.singleLinkView.isVisible
    private fun isImageView() = binding.singleImageList.isVisible
    private fun isDocumentVisible() = binding.singleDocumentView.isVisible

    private fun readableFileSize(size: Long): String {
        if (size <= 0) return "0"
        val units = arrayOf("B", "kB", "MB", "GB", "TB")
        val digitGroups = (log10(size.toDouble()) / log10(1024.0)).toInt()
        return DecimalFormat("#,##0.#").format(size / 1024.0.pow(digitGroups.toDouble())).toString() + " " + units[digitGroups]
    }
}
