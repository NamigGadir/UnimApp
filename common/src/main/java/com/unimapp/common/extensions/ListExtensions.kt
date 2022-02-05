package com.unimapp.common.extensions

fun <T> Iterable<T>.firstIndexOrNull(predicate: (T) -> Boolean): Int? {
    this.forEachIndexed { index, element ->
        if (predicate(element)) return index
    }
    return null
}

fun <T> fakeArrayListOf(clazz: Class<T>, size: Int = 10): ArrayList<T> {
    return arrayListOf<T>().also {
        for (i in 0..size) {
            val instance = clazz.newInstance()
            clazz.declaredFields.forEach {
                if (it.type.isInstance(String())) {
                    it.set(instance, "sdfsdfsdf")
                }
            }
            it.add(instance)
        }
    }
}
