package com.unimapp.uitoolkit.list_item

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
import com.unimapp.common.extensions.addBorder
import com.unimapp.common.extensions.getResourceIdifHas
import com.unimapp.uitoolkit.R
import com.unimapp.uitoolkit.databinding.FeedImageViewBinding
import com.unimapp.uitoolkit.databinding.FeedResourceItemBinding
import com.unimapp.uitoolkit.databinding.FileResourceViewItemBinding
import com.unimapp.uitoolkit.databinding.ImageResourceViewItemBinding

class FeedResourceView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: FeedResourceItemBinding = FeedResourceItemBinding.inflate(LayoutInflater.from(context), this, true)
    private var mOnDownloadFile: ((id: Long, location: String) -> Unit)? = null

    init {
        binding.imageViewMain.addBorder(solidColor = R.color.stroke_color_alpha05, radius = 16f)
    }

    fun setResource(feedResource: FeedResource) {
        binding.resourceName.text = feedResource.name
        binding.resourceSize.text = feedResource.size
        binding.downloadImage.setOnClickListener {
            mOnDownloadFile?.invoke(feedResource.id, feedResource.location)
        }
    }

    fun setOnDownload(onDownloadFile: (id: Long, location: String) -> Unit) {
        this.mOnDownloadFile = onDownloadFile
    }

    class FeedResource(val id: Long, val name: String, val size: String, val location: String)
}


