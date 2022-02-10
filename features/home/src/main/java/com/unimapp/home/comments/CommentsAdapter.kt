package com.unimapp.home.comments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.unimapp.common.extensions.getString
import com.unimapp.common.extensions.gone
import com.unimapp.common.extensions.show
import com.unimapp.domain.entities.feed.Comment
import com.unimapp.home.R
import com.unimapp.home.databinding.CommentListItemBinding
import com.unimapp.uitoolkit.base.BaseAdapter
import com.unimapp.uitoolkit.comment.CommentItemView

class CommentsAdapter : BaseAdapter<Comment, CommentsAdapter.CommentViewHolder>() {

    private var mCommentsActionListener: CommentsActionListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(CommentListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.commentItemBinding.mainComment.setOnReplyComment { userId, userName ->
            mCommentsActionListener?.onCommentReply(userId, userName)
        }
    }


    fun setActionListener(commentsActionListener: CommentsActionListener) {
        mCommentsActionListener = commentsActionListener
    }

    inner class CommentViewHolder(val commentItemBinding: CommentListItemBinding) : RecyclerView.ViewHolder(commentItemBinding.root) {

        fun bind(comment: Comment) {
            commentItemBinding.mainComment.setCommentItemModel(CommentItemView.CommentItemModel(
                comment.id, "Idrak Atakisili",
                "https://www.monotsuites.com/assets/pages/media/profile/profile.jpg",
                78
            ))
            if (comment.replyCount > 0) {
                commentItemBinding.commentResponses.show()
                commentItemBinding.viewReplies.text =
                    when {
                        (comment.subComments?.size ?: 0) == 0 -> {
                            commentItemBinding.getString(R.string.view_replies, comment.replyCount)
                        }
                        comment.replyCount > comment.subComments?.size ?: 0 -> {
                            commentItemBinding.getString(R.string.view_replies, comment.replyCount - (comment.subComments?.size ?: 0))
                        }
                        else -> commentItemBinding.getString(R.string.hide_replies)
                    }
                commentItemBinding.viewReplies.setOnClickListener {
                    when {
                        comment.replyCount <= (comment.subComments?.size ?: 0) -> {
                            if (commentItemBinding.responses.isVisible) {
                                commentItemBinding.responses.gone()
                                commentItemBinding.viewReplies.text = commentItemBinding.getString(R.string.view_replies, comment.replyCount)
                            } else {
                                commentItemBinding.viewReplies.text = commentItemBinding.getString(R.string.hide_replies)
                                commentItemBinding.responses.show()
                            }
                        }
                        else -> {
                            mCommentsActionListener?.onLoadComments(comment)
                            commentItemBinding.responses.show()
                        }
                    }
                }
                comment.subComments?.forEach {
                    val commentItem = CommentItemView(commentItemBinding.root.context)
                    commentItem.setOnReplyComment { userId, userName ->
                        mCommentsActionListener?.onCommentReply(userId, userName)
                    }
                    commentItem.setCommentItemModel(
                        CommentItemView.CommentItemModel(
                            it.id, "Namig",
                            "https://www.monotsuites.com/assets/pages/media/profile/profile.jpg",
                            4
                        )
                    )
                    commentItemBinding.responses.addView(commentItem)
                }
            } else {
                commentItemBinding.commentResponses.gone()
            }
        }
    }

    interface CommentsActionListener {
        fun onCommentLike(comment: Comment)
        fun onCommentReply(userId: Long, userName: String)
        fun onLoadComments(comment: Comment)
    }
}
