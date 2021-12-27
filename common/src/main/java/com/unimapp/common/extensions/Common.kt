package com.unimapp.common.extensions

import android.content.res.TypedArray


fun TypedArray.getResourceIdifHas(id: Int, default: Int): Int? {
    return if (hasValue(id))
        getResourceId(id, default)
    else null
}

