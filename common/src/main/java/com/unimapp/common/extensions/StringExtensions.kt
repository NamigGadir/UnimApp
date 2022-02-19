package com.unimapp.common.extensions


fun String.asTag(): CharSequence {
    return "@$this "
}

