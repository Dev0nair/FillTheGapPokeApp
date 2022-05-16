package com.igonris.common.types

sealed class ErrorType(val desc: String) {

    class APIError(desc: String): ErrorType(desc)
    class Other(desc: String): ErrorType(desc)
}
