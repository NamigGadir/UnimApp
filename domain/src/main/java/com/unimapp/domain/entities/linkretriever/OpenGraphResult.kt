package com.unimapp.domain.entities.linkretriever

data class LinkRetrieverResult(
    var title: String? = null,
    var description: String? = null,
    var url: String? = null,
    var image: String? = null,
    var siteName: String? = null,
    var type: String? = null
)