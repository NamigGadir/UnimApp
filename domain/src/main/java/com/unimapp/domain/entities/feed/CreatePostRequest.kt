package com.unimapp.domain.entities.feed

class CreatePostRequest(
    val caption: String,
    val externalLink: String? = null,
    val files: List<String>? = null,
    val postType: String,
    val sharedFeedId: String? = null,
    val userTaggedId: List<String>? = null
)