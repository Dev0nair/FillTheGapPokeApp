package com.igonris.common

sealed class ErrorType {

    class APIError(val desc: String): ErrorType()
}
