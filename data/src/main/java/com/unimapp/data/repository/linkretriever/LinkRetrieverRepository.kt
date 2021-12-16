package com.unimapp.data.repository.linkretriever

import com.unimapp.data.extensions.toHttpsPrefix
import com.unimapp.domain.entities.linkretriever.LinkRetrieverResult
import kotlinx.coroutines.flow.flow
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.jsoup.Jsoup
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LinkRetrieverRepository @Inject constructor() {
    companion object {
        private const val AGENT = "Mozilla"
        private const val REFERRER = "http://www.google.com"
        private const val TIMEOUT = 20000
        private const val DOC_SELECT_QUERY = "meta[property^=og:]"
        private const val OPEN_GRAPH_KEY = "content"
        private const val PROPERTY = "property"
        private const val OG_IMAGE = "og:image"
        private const val OG_DESCRIPTION = "og:description"
        private const val OG_URL = "og:url"
        private const val OG_TITLE = "og:title"
        private const val OG_SITE_NAME = "og:site_name"
        private const val OG_TYPE = "og:type"
    }

    fun retrieveLinkData(url: String) = flow {

        val linkRetrieverResult = LinkRetrieverResult()
        try {
            val response = Jsoup.connect(url.toHttpsPrefix())
                .ignoreContentType(true)
                .userAgent(AGENT)
                .referrer(REFERRER)
                .timeout(TIMEOUT)
                .followRedirects(true)
                .execute()

            val doc = response.parse()

            val ogTags = doc.select(DOC_SELECT_QUERY)
            if (ogTags.size > 0) {
                ogTags.forEachIndexed { index, _ ->
                    val tag = ogTags[index]

                    when (tag.attr(PROPERTY)) {
                        OG_IMAGE -> {
                            linkRetrieverResult.image = (tag.attr(OPEN_GRAPH_KEY))
                        }
                        OG_DESCRIPTION -> {
                            linkRetrieverResult.description = (tag.attr(OPEN_GRAPH_KEY))
                        }
                        OG_URL -> {
                            linkRetrieverResult.url = (tag.attr(OPEN_GRAPH_KEY))
                        }
                        OG_TITLE -> {
                            linkRetrieverResult.title = (tag.attr(OPEN_GRAPH_KEY))
                        }
                        OG_SITE_NAME -> {
                            linkRetrieverResult.siteName = (tag.attr(OPEN_GRAPH_KEY))
                        }
                        OG_TYPE -> {
                            linkRetrieverResult.type = (tag.attr(OPEN_GRAPH_KEY))
                        }
                    }
                }
                emit(linkRetrieverResult)
            } else
                emit(null)
        } catch (e: Exception) {
            emit(null)
        }
    }
}