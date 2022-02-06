package com.unimapp.uitoolkit.tagview

import androidx.annotation.DrawableRes
import com.unimapp.uitoolkit.R

/**
 * This data class is the info class of tagview.
 * @see TagView
 */
data class TagItem(
    val id: Long?, val text: String?, var isSelected: Boolean, val tagTextColor: Int, val background: Int,
    val selectedTextBackgroundColor: Int, val selectedBackground: Int, val tagTextSize: Int,
    @DrawableRes var tagImage: Int? = null
)

/**
 * Tag item builder. Builds tag item for given tag data info
 */
class TagItemBuilder {
    var id: Long? = null
    var text: String? = null
    var isSelected: Boolean? = null
    var tagTextColor: Int? = null
    var backgroundResurce: Int? = null
    var selectedTextBackgroundColor: Int? = null
    var selectedBackgroundResource: Int? = null
    var tagTextSize: Int? = null
    var tagImage: Int? = null

    inline fun id(id: () -> Long) {
        this.id = id()
    }

    inline fun text(text: () -> String) {
        this.text = text()
    }

    inline fun isSelected(isSelected: () -> Boolean) {
        this.isSelected = isSelected()
    }

    inline fun tagTextColor(tagTextColor: () -> Int) {
        this.tagTextColor = tagTextColor()
    }

    inline fun backGroundColor(backGroundColor: () -> Int) {
        this.backgroundResurce = backGroundColor()
    }

    inline fun selectedTextBackgroundColor(selectedTextBackgroundColor: () -> Int) {
        this.selectedTextBackgroundColor = selectedTextBackgroundColor()
    }

    inline fun selectedBackgroundColor(selectedBackgroundColor: () -> Int) {
        this.selectedBackgroundResource = selectedBackgroundColor()
    }

    inline fun tagTextSize(tagTextSize: () -> Int) {
        this.tagTextSize = tagTextSize()
    }

    inline fun tagImage(tagImage: () -> Int) {
        this.tagImage = tagImage()
    }
    fun build() = TagItem(
        id,
        text,
        isSelected ?: false,
        tagTextColor ?: R.color.white,
        backgroundResurce ?: R.drawable.tagview_default_background,
        selectedTextBackgroundColor ?: R.color.white,
        selectedBackgroundResource ?: R.drawable.tagview_selected_background,
        tagTextSize ?: TagConstants.DEFAULT_TAG_TEXT_SIZE,
        tagImage
    )
}


fun tagItem(lambda: TagItemBuilder.() -> Unit) =
    TagItemBuilder().apply(lambda).build()