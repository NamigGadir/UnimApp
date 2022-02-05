package com.unimapp.uitoolkit.search

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import coil.load
import com.unimapp.common.extensions.addBorder
import com.unimapp.common.extensions.dp
import com.unimapp.common.extensions.gone
import com.unimapp.uitoolkit.R
import com.unimapp.uitoolkit.databinding.CustomSearchViewBinding
import com.unimapp.uitoolkit.databinding.ImageResourceViewItemBinding
import com.unimapp.uitoolkit.databinding.LinkResourceViewItemBinding
import com.unimapp.uitoolkit.databinding.ReactionViewBinding
import de.hdodenhof.circleimageview.CircleImageView
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import com.unimapp.common.extensions.hideKeyboard


class CustomSearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: CustomSearchViewBinding = CustomSearchViewBinding.inflate(LayoutInflater.from(context), this, true)
    private var mOnSearchAction: ((searchTitle: String) -> Unit)? = null

    init {
        binding.mainLayout.addBorder(strokeColor = R.color.stroke_color_alpha40, radius = 12.dp.toFloat(), strokeWidth = 1.dp)
        binding.searchTitle.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                mOnSearchAction?.invoke(binding.searchTitle.text.toString())
                context.hideKeyboard(binding.searchTitle)
                return@OnEditorActionListener true
            }
            false
        })
    }

    fun setOnSearchAction(onSearch: (searchTitle: String) -> Unit) {
        mOnSearchAction = onSearch
    }


}