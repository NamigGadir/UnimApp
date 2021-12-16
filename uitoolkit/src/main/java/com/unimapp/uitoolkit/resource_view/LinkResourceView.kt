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
import com.unimapp.uitoolkit.databinding.LinkResourceViewItemBinding

class LinkResourceView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var onDeleteClicked: (() -> Unit)? = null
    private var binding: LinkResourceViewItemBinding = LinkResourceViewItemBinding.inflate(LayoutInflater.from(context), this, true)

    private var imageResource: Int? = null
    private var linkTitle: String? = null
    private var link: String? = null
    private var imageLink: String? = null


    init {
        context.obtainStyledAttributes(attrs, R.styleable.LinkResourceView, 0, 0).apply {
            imageResource = getResourceIdifHas(R.styleable.LinkResourceView_link_image, 0)
            linkTitle = getString(R.styleable.LinkResourceView_link)
            link = getString(R.styleable.LinkResourceView_link)
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
        setViewInfo()
    }

    fun setViewInfo() {
        linkTitle?.let { binding.linkTitle.text = linkTitle }?:run{
            binding.linkTitle.text = link
        }
        link?.let { binding.linkUrl.text = link }
        imageLink?.let {
            binding.mainImage.load(imageLink)
        }
    }

    fun setOnDeleteClicked(onDeleteClicked: () -> Unit) {
        this.onDeleteClicked = onDeleteClicked
    }

    fun setLinkInfo(linkInfo: LinkInfo) {
        this.linkTitle = linkInfo.linkTitle
        this.link = linkInfo.link
        this.imageLink = linkInfo.linkImage
        setViewInfo()
    }

    class LinkInfo(
        val linkTitle: String?,
        val link: String?,
        val linkImage: String?
    )
}


