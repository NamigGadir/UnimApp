package com.unimapp.uitoolkit.resource_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import coil.load
import com.unimapp.common.extensions.getResourceIdifHas
import com.unimapp.uitoolkit.R
import com.unimapp.uitoolkit.databinding.FileResourceViewItemBinding
import com.unimapp.uitoolkit.databinding.ImageResourceViewItemBinding

class FileResourceView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var onDeleteClicked: (() -> Unit)? = null
    private var binding: FileResourceViewItemBinding = FileResourceViewItemBinding.inflate(LayoutInflater.from(context), this, true)

    @DrawableRes
    private var imageResource: Int? = null
    private var documentName: String? = null
    private var documentSize: String? = null

    init {
        context.obtainStyledAttributes(attrs, R.styleable.FileResourceView, 0, 0).apply {
            imageResource = getResourceIdifHas(R.styleable.FileResourceView_document_logo, 0)
            documentName = getString(R.styleable.FileResourceView_document_name)
            documentSize = getString(R.styleable.FileResourceView_document_size)
            recycle()
            initView()
        }
    }

    private fun initView() {
        binding.closeImage.setOnClickListener {
            onDeleteClicked?.invoke()
        }
        imageResource?.let {
            binding.mainImage.setImageResource(it)
        }
        binding.documentName.text = documentName
        binding.documentSize.text = documentSize
    }

    fun setOnDeleteClicked(onDeleteClicked: () -> Unit) {
        this.onDeleteClicked = onDeleteClicked
    }

    fun setDocumentInfo(documentInfo: DocumentInfo) {
        this.documentName = documentInfo.documentName
        this.documentSize = documentInfo.documentSize
        initView()
    }

    class DocumentInfo(
        val documentName: String,
        val documentSize: String,
        val documentType: DocumentType
    )

    enum class DocumentType {
        IMAGE,
        PDF
    }
}


