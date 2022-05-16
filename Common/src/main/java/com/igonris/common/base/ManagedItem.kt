package com.igonris.common.base

class ManagedItem<T>(private val item: T) {

    private var handled = false

    private fun isHandled() = handled

    fun doIfUnhandled(action: (T) -> Unit) {
        if(!isHandled()) {
            handled = true
            action(item)
        }
    }
}