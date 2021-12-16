package com.unimapp.common.extensions

import android.graphics.drawable.GradientDrawable
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat

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


fun View.showPopupMenu(menu: Int, onMenuItemClick: (MenuItem) -> Boolean, invisibleItems: List<Int>?=null) {
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


fun View.addBorder(@ColorRes solidColor: Int? = null, @ColorRes strokeColor: Int? = null, radius: Float? = null, strokeWidth: Int) {
    val border = GradientDrawable()
    solidColor?.let { border.setColor(ContextCompat.getColor(context, solidColor)) }
    strokeColor?.let { border.setStroke(strokeWidth, ContextCompat.getColor(context, strokeColor)) }
    radius?.let { border.cornerRadius = radius }
    background = border
}
