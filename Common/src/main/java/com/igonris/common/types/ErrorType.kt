package com.igonris.common.types

sealed class ErrorType {

    class APIError(val desc: String): ErrorType()
}
