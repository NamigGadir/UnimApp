package com.unimapp.common.extensions

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.view.ViewTreeObserver
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.unimapp.common.R
import kotlinx.coroutines.*

fun TextView.underline() {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

fun EditText.afterTextChangedDebounce(delayMillis: Long, input: (String) -> Unit) {
    var lastInput = ""
    var debounceJob: Job? = null
    val uiScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            if (editable != null) {
                val newtInput = editable.toString()
                debounceJob?.cancel()
                if (lastInput != newtInput) {
                    lastInput = newtInput
                    debounceJob = uiScope.launch {
                        delay(delayMillis)
                        if (lastInput == newtInput) {
                            input(newtInput)
                        }
                    }
                }
            }
        }

        override fun beforeTextChanged(cs: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(cs: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

/**
 * This code has taken from [https://stackoverflow.com/questions/19675331/add-view-more-at-the-end-of-textview-after-3-lines]
 * and changed by Namig Gadirov
 */
fun TextView.makeTextViewResizable(maxLine: Int, viewMore: Boolean, moreTitle: String, lessTitle: String, spanColor: Int) {
    val expandText = if (viewMore) moreTitle else lessTitle
    if (tag == null) {
        tag = text
    }
    val vto = viewTreeObserver
    vto.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            val mainText: String
            val lineEndIndex: Int
            val obs = viewTreeObserver
            obs.removeOnGlobalLayoutListener(this)
            when (maxLine) {
                0 -> {
                    lineEndIndex = layout.getLineEnd(0)
                    mainText = text.subSequence(0, lineEndIndex - expandText.length + 1).toString() + " " + expandText
                }
                in 1..lineCount -> {
                    lineEndIndex = layout.getLineEnd(maxLine - 1)
                    mainText = text.subSequence(0, lineEndIndex - expandText.length + 1).toString() + " " + expandText
                }
                else -> {
                    lineEndIndex = layout.getLineEnd(layout.lineCount - 1)
                    mainText = text.subSequence(0, lineEndIndex).toString() + " " + expandText
                }
            }
            text = mainText
            movementMethod = LinkMovementMethod.getInstance()
            setText(
                addClickablePartTextViewResizable(
                    SpannableString(text.toString()), expandText,
                    viewMore, moreTitle, lessTitle,
                    spanColor
                ), TextView.BufferType.SPANNABLE
            )
        }
    })
}

private fun TextView.addClickablePartTextViewResizable(strSpanned: Spanned, spanableText: String, viewMore: Boolean, moreText: String, lessText: String, spanColor: Int): SpannableStringBuilder {
    val str = strSpanned.toString()
    val ssb = SpannableStringBuilder(strSpanned)
    val fromIndex = str.indexOf(spanableText)
    val endIndex = str.indexOf(spanableText) + spanableText.length
    if (str.contains(spanableText)) {
        ssb.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                layoutParams = layoutParams
                setText(tag.toString(), TextView.BufferType.SPANNABLE)
                invalidate()
                if (viewMore) {
                    makeTextViewResizable(-1, false, moreText, lessText, spanColor)
                } else {
                    makeTextViewResizable(3, true, moreText, lessText, spanColor)
                }
            }
        }, fromIndex, endIndex, 0)
    }
    return ssb.also {
        it.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, spanColor)), fromIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        it.setSpan(StyleSpan(Typeface.BOLD), fromIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}