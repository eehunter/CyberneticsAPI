package com.oyosite.ticon.cyberlib.util

fun <T> MutableList<T>.push(item: T): T {
    add(item)
    return item
}