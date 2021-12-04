package com.unimapp.unimapp.testingcodes

fun main() {
    val regex by lazy { "(.*[A-Z].*)".toRegex() }
    println("dfsdfsdfAA".matches(regex))
}