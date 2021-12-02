package com.unimapp.common.extensions

import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.annotation.StringRes
import androidx.appcompat.widget.PopupMenu

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
