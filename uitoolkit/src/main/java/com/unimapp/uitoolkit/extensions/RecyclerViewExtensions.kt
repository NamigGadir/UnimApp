package com.unimapp.uitoolkit.extensions

import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.unimapp.uitoolkit.list.InsetItemDecoration
import com.unimapp.uitoolkit.list.SingleRecyclerViewAdapter


fun <T : Any, B : ViewBinding> RecyclerView.withSingleAdapter(
    listItems: List<T>,
    bindingCallback: (ViewGroup?, Boolean) -> B,
    onBindView: B.(data: T) -> Unit,
    onItemClick: (B.(data: T) -> Unit)? = null,
): RecyclerView {
    val adapter = SingleRecyclerViewAdapter(listItems, bindingCallback, onBindView, onItemClick)
    this.adapter = adapter
    return this
}


fun RecyclerView.addDivider(
    orientation: Int = LinearLayoutManager.VERTICAL,
    @Px insetStart: Int = 0,
    @Px insetEnd: Int = 0,
    @Px height: Int = 0,
    @ColorInt color: Int? = null,
) {
    ColorDrawable(color ?: 0x000000).let {
        val dividerItemDecoration = InsetItemDecoration(
            divider = it,
            leftInset = insetStart,
            rightInset = insetEnd,
            color = color,
            height = height
        ).apply {
            this.orientation = orientation
        }
        addItemDecoration(dividerItemDecoration)
    }
}
