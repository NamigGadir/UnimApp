package com.unimapp.common.extensions

import android.graphics.drawable.GradientDrawable
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.viewbinding.ViewBinding
import com.google.android.material.textfield.TextInputLayout
import java.util.*

fun Button.onClick(listener: View.OnClickListener) {
    this.setOnClickListener(listener)
}

fun View.gone() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.getString(@StringRes resId: Int) = context.getString(resId)
fun View.getString(@StringRes resId: Int, vararg list: Any) = context.getString(resId, list)


fun View.showPopupMenu(menu: Int, onMenuItemClick: (MenuItem) -> Boolean, invisibleItems: List<Int>? = null) {
    PopupMenu(context, this).apply {
        inflate(menu)
        invisibleItems?.map {
            getMenu().findItem(it).setVisible(false)
        }
        setOnMenuItemClickListener { item ->
            onMenuItemClick(item)
        }
    }.show()
}


fun View.addBorder(@ColorRes solidColor: Int? = null, @ColorRes strokeColor: Int? = null, radius: Float? = null, strokeWidth: Int = 0) {
    val border = GradientDrawable()
    solidColor?.let { border.setColor(ContextCompat.getColor(context, solidColor)) }
    strokeColor?.let { border.setStroke(strokeWidth, ContextCompat.getColor(context, strokeColor)) }
    radius?.let { border.cornerRadius = radius }
    background = border
}

fun ViewBinding.getString(@StringRes stringResourceId: Int): String {
    return root.context.getString(stringResourceId)
}

fun ViewBinding.getString(@StringRes stringResourceId: Int, vararg extras: Any): String {
    return root.context.getString(stringResourceId, *extras)
}

fun TextInputLayout.doOnTextChanged(action: (CharSequence?, Int, Int, Int) -> Unit) {
    editText?.doOnTextChanged { text, start, before, count ->
        action(text, start, before, count)
    }
}

fun TextInputLayout.onTextChanged(action: (text: CharSequence?) -> Unit) {
    editText?.doOnTextChanged { text, _, _, _ ->
        action(text)
    }
}
