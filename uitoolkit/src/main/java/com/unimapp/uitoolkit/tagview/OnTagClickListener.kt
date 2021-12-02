package com.unimapp.uitoolkit.tagview

import com.unimapp.uitoolkit.tagview.TagItem

interface OnTagClickListener {
    fun onTagClick(tag: TagItem, position: Int)
}