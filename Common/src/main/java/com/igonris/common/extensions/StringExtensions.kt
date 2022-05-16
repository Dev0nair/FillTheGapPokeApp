package com.igonris.common.extensions

fun String.firstUpperThenLower(): String {
    return if(this.length > 1) {
        this[0].uppercase() + this.substring(1, this.length).lowercase()
    } else {
        this.uppercase()
    }
}