package com.unimapp.unimapp.di

import android.webkit.URLUtil
import java.util.regex.Matcher
import java.util.regex.Pattern

fun main() {
    val urlPattern = Pattern.compile(
        "([\\w+]+://)?([\\w\\d-]+\\.)*[\\w-]+[.:]\\w+([/?=&#.]?[\\w-]+)*/?",
        Pattern.CASE_INSENSITIVE or Pattern.MULTILINE or Pattern.DOTALL
    )
    val string = "foo bar  https://stackoverflow.com/questions/29080840/why-is-java-util-regex-matcher-start-and-end-returning-extra-characters-in-t xample.com baz"
    val matcher = urlPattern.matcher(string)
    if (matcher.find()) {
        val matchStart = matcher.start()
        val matchEnd = matcher.end()
        // now you have the offsets of a URL match
        val link = string.substring(matchStart, matchEnd)
        println(link)
    }
}