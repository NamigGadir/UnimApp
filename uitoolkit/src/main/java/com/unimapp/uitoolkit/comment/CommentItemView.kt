package com.unimapp.uitoolkit.comment

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import com.unimapp.uitoolkit.R
import com.unimapp.uitoolkit.databinding.CommentItemBinding

class CommentItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: CommentItemBinding = CommentItemBinding.inflate(LayoutInflater.from(context), this, true)
    private var mOnReplyComment: ((userId: Long, userName: String) -> Unit)? = null
    private var mOnLikeComment: ((userId: Long) -> Boolean)? = null
    private var mCommentItemModel: CommentItemModel? = null

    fun setCommentItemModel(commentItemModel: CommentItemModel) {
        this.mCommentItemModel = commentItemModel
        binding.userName.text = commentItemModel.userName
        binding.userImage.load(commentItemModel.userImage)
        setLikeCount(commentItemModel.likeCount)
        binding.reply.setOnClickListener {
            mOnReplyComment?.invoke(commentItemModel.id, commentItemModel.userName)
        }

        binding.pulsesCount.setOnClickListener {
            setLikeCount(commentItemModel.likeCount + 1)
            val result = mOnLikeComment?.invoke(commentItemModel.id) ?: false
            if (!result)
                setLikeCount(commentItemModel.likeCount)
        }
    }

    private fun setLikeCount(count: Int) {
        binding.pulsesCount.text = context.getString(R.string.pulses_count, count)

    }

    fun setOnLikeComment(onLikeComment: (id: Long) -> Boolean) {
        this.mOnLikeComment = onLikeComment
    }

    fun setOnReplyComment(onReplyComment: ((userId: Long, userName: String) -> Unit)) {
        this.mOnReplyComment = onReplyComment
    }

    class CommentItemModel(val id: Long, val userName: String, val userImage: String, val likeCount: Int)
}


