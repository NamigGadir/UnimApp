package com.unimapp.uitoolkit.input

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import coil.load
import com.unimapp.common.extensions.addBorder
import com.unimapp.common.extensions.dp
import com.unimapp.common.extensions.getResourceIdifHas
import com.unimapp.uitoolkit.R
import com.unimapp.uitoolkit.databinding.ImageResourceViewItemBinding
import com.unimapp.uitoolkit.databinding.InputWithHeaderViewBinding

class InputWithHeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: InputWithHeaderViewBinding = InputWithHeaderViewBinding.inflate(LayoutInflater.from(context), this, true)
    private var headerTitle: String?
    private var inputText: String?
    private var inputHint: String?

    init {
        context.obtainStyledAttributes(attrs, R.styleable.InputWithHeaderView, 0, 0).apply {
            headerTitle = getString(R.styleable.InputWithHeaderView_header)
            inputText = getString(R.styleable.InputWithHeaderView_input_text)
            inputHint = getString(R.styleable.InputWithHeaderView_input_hint)
            recycle()
            initView()
        }
    }

    private fun initView() {
        binding.input.hint = inputHint
        binding.input.setText(inputText)
        binding.headerTitle.text = headerTitle
        binding.input.addBorder(strokeColor = R.color.unim_text_2, radius = 16.dp.toFloat(), strokeWidth = 1.dp)
    }

    fun onTextChanged(onTextChanged: (String) -> Unit) {
        binding.input.doOnTextChanged { text, _, _, _ ->
            onTextChanged.invoke(text.toString())
        }
    }
}
