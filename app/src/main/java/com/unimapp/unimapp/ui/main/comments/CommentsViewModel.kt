package com.unimapp.unimapp.ui.main.comments

import androidx.lifecycle.viewModelScope
import com.ingress.core.BaseViewModel
import com.unimapp.domain.entities.feed.Comment
import com.unimapp.domain.repository.FeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val feedRepository: FeedRepository
) : BaseViewModel<CommentState, Unit>() {

    fun loadComments() {
        feedRepository.loadComments()
            .onEach {
                postState(CommentState.CommentList(it))
            }.launchIn(viewModelScope)
    }

    fun loadMoreComments(comment: Comment) {
        viewModelScope.launch {
            comment.subComments = arrayListOf<Comment>(
                Comment(System.currentTimeMillis(), "dadasdasdasdasd"),
                Comment(System.currentTimeMillis() + 1, "ewerwer"),
                Comment(System.currentTimeMillis() + 5, "erwerdfsdfadsffdsfs"),
            ) + (comment.subComments ?: arrayListOf())
            postState(CommentState.OnCommentChange(comment))
        }
    }
}

sealed class CommentState {
    class CommentList(val comments: List<Comment>) : CommentState()
    class OnCommentChange(val comment: Comment) : CommentState()
}


