package com.unimapp.common.extensions

import android.view.View
import android.widget.Button


fun Button.onClick(listener: View.OnClickListener) {
    this.setOnClickListener(listener)
}
