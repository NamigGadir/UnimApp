package com.unimapp.uitoolkit.resource_view

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import com.unimapp.common.extensions.getResourceIdifHas
import com.unimapp.uitoolkit.R
import com.unimapp.uitoolkit.databinding.ImageResourceViewItemBinding

/**
 * This class is responsible for signature
 */
class ImageResourceView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var onDeleteClicked: (() -> Unit)? = null
    private var binding: ImageResourceViewItemBinding = ImageResourceViewItemBinding.inflate(LayoutInflater.from(context), this, true)

    @DrawableRes
    private var imageResource: Int? = null

    init {
        context.obtainStyledAttributes(attrs, R.styleable.ImageResourceView, 0, 0).apply {
            imageResource = getResourceIdifHas(R.styleable.ImageResourceView_image, 0)
            recycle()
            initView()
        }
    }

    private fun initView() {
        binding.closeImage.setOnClickListener {
            onDeleteClicked?.invoke()
        }

    }

    fun setOnDeleteClicked(onDeleteClicked: () -> Unit) {
        this.onDeleteClicked = onDeleteClicked
    }

    fun setImageResource(imageUri: Uri) {
        binding.mainImage.setImageResource(R.drawable.profile_image)
    }

}
