package com.unimapp.uitoolkit.comment

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import coil.load
import com.unimapp.common.extensions.addBorder
import com.unimapp.common.extensions.getResourceIdifHas
import com.unimapp.common.extensions.getString
import com.unimapp.uitoolkit.R
import com.unimapp.uitoolkit.databinding.*

class CommentItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: CommentItemBinding = CommentItemBinding.inflate(LayoutInflater.from(context), this, true)
    private var mOnReplyComment: ((userId: Long, userName: String) -> Boolean)? = null
    private var mOnLikeComment: ((userId: Long) -> Boolean)? = null

    fun setCommentItemModel(commentItemModel: CommentItemModel) {
        binding.userName.text = commentItemModel.userName
        binding.userImage.load(commentItemModel.userImage) {
            listener(
                onError = { _, it ->
                    it
                }
            )
        }
        setLikeCount(commentItemModel.likeCount)
        binding.reply.setOnClickListener {
            setLikeCount(commentItemModel.likeCount + 1)
            val result = mOnLikeComment?.invoke(commentItemModel.id) ?: false
            if (!result)
                setLikeCount(commentItemModel.likeCount)
        }
    }

    private fun setLikeCount(count: Int) {
        binding.pulsesCount.text = context.getString(R.string.pulses_count, count)

    }

    fun setActions(onReplyComment: (id: Long, location: String) -> Boolean, onLikeComment: (id: Long) -> Boolean) {
        this.mOnReplyComment = onReplyComment
        this.mOnLikeComment = onLikeComment
    }

    class CommentItemModel(val id: Long, val userName: String, val userImage: String, val likeCount: Int)
}


