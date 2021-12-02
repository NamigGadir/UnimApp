package com.unimapp.common.extensions

fun <T> Iterable<T>.firstIndexOrNull(predicate: (T) -> Boolean): Int? {
    this.forEachIndexed { index, element ->
        if (predicate(element)) return index
    }
    return null
}