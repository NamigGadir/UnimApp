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
import com.unimapp.uitoolkit.databinding.*

class FeedLinkView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: FeedLinkItemBinding = FeedLinkItemBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding.mainView.addBorder(solidColor = R.color.stroke_color_alpha05, radius = 16f)
    }

}


