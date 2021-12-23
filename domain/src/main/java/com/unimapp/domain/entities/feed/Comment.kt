package com.unimapp.domain.entities.feed

class Comment(
    val id: Long,
    val content: String? = null,
    val replyCount: Int = 6,
    var subComments: List<Comment>? = null,
)