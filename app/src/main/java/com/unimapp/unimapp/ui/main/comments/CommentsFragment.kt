package com.unimapp.unimapp.ui.main.comments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.unimapp.common.extensions.asTag
import com.unimapp.domain.entities.feed.Comment
import com.unimapp.uitoolkit.extensions.addDivider
import com.unimapp.core.BaseFragment
import com.unimapp.unimapp.databinding.FragmentCommentsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentsFragment : BaseFragment<CommentsViewModel, FragmentCommentsBinding, CommentState, Unit>() {
    private val adapter by lazy { CommentsAdapter() }

    override fun getViewModelClass() = CommentsViewModel::class.java

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCommentsBinding
        get() = FragmentCommentsBinding::inflate

    override val onViewInit: FragmentCommentsBinding.() -> Unit = {
        val navHostFragment = NavHostFragment.findNavController(this@CommentsFragment)
        NavigationUI.setupWithNavController(toolbar, navHostFragment)
        commentsList.adapter = adapter
        adapter.setActionListener(object : CommentsAdapter.CommentsActionListener {
            override fun onCommentLike(comment: Comment) {

            }

            override fun onCommentReply(userId: Long, userName: String) {
                showReplyUser(userId, userName)
            }

            override fun onLoadComments(comment: Comment) {
                viewmodel.loadMoreComments(comment)
            }
        })
        commentsList.addDivider(height = 55)
        viewmodel.loadComments()
    }

    private fun showReplyUser(userId: Long, userName: String) {
        userName.asTag().let {
            binding.commentInput.setText(it)
            binding.commentInput.setSelection(it.length)
        }
    }

    override fun onStateUpdate(state: CommentState) {
        when (state) {
            is CommentState.CommentList -> {
                showComments(state.comments)
            }
            is CommentState.OnCommentChange -> {
                val pos = adapter.currentList.indexOf(state.comment)
                adapter.notifyItemChanged(pos)
            }
        }
    }

    private fun showComments(comments: List<Comment>) {
        adapter.submitList(comments)
    }
}