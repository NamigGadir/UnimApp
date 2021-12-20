package com.unimapp.uitoolkit.list_item

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager2.widget.ViewPager2
import com.unimapp.uitoolkit.databinding.FeedImageViewBinding

import com.unimapp.common.extensions.dp
import com.unimapp.common.extensions.gone
import com.unimapp.uitoolkit.R


class FeedImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: FeedImageViewBinding = FeedImageViewBinding.inflate(LayoutInflater.from(context), this, true)
    private var dots: List<ImageView>? = null
    private var mImagesList: List<String>? = null

    fun setImages(imagesList: List<String>) {
        val adapter = FeedImagesAdapter()
        binding.imagesList.adapter = adapter
        adapter.submitList(imagesList)
        if (imagesList.size > 1) {
            binding.imagesList.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    selectDot(position)
                }
            })
            addDots(imagesList)
        } else
            binding.dots.gone()
        mImagesList = imagesList
    }

    private fun addDots(imagesList: List<String>) {
        val untilSize = imagesList.size
        val dots = ArrayList<ImageView>()
        for (i in 0 until untilSize) {
            val dot = ImageView(context)
            dot.setPadding(4.dp, 10, 4.dp, 0)
            dot.setImageResource(R.drawable.ic_dot_unselected)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            binding.dots.addView(dot, params)
            dots.add(dot)
        }
        this.dots = dots
    }

    private fun selectDot(idx: Int) {
        val untilSize = mImagesList?.size ?: 0
        for (i in 0 until untilSize) {
            val drawableId = if (i == idx) R.drawable.ic_dot_selected else R.drawable.ic_dot_unselected
            dots?.get(i)?.setImageResource(drawableId)
        }
    }
}
