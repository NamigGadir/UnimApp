package com.unimapp.data.extensions

fun String.toHttpsPrefix(): String = if (isNotEmpty() && !startsWith("https://") && !startsWith("http://")) {
    "https://$this"
} else if (startsWith("http://")) {
    replace("http://", "https://")
} else this


fun String.toBearer(): String {
    return "Bearer $this"
}
