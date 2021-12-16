package com.unimapp.domain.validators

import com.unimapp.domain.base.BaseValidator
import java.util.regex.Pattern
import javax.inject.Inject

class UrlValidator @Inject constructor() : BaseValidator<String> {
    private  val urlPattern = Pattern.compile(
        "([\\w+]+://)?([\\w\\d-]+\\.)*[\\w-]+[.:]\\w+([/?=&#.]?[\\w-]+)*/?",
        Pattern.CASE_INSENSITIVE or Pattern.MULTILINE or Pattern.DOTALL
    )

    override fun isValid(input: String): Boolean {
        val matcher = urlPattern.matcher(input)
        return matcher.find()
    }

    fun findUrl(input: String): String? {
        val matcher = urlPattern.matcher(input)
        return if (matcher.find()) {
            val matchStart = matcher.start()
            val matchEnd = matcher.end()
            input.substring(matchStart, matchEnd)
        } else null
    }
}