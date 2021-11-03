package com.unimapp.common.extensions

import android.graphics.Paint
import android.widget.TextView

fun TextView.underline() {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}