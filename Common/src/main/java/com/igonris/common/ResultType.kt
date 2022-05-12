package com.igonris.common

sealed class ResultType <T> {

    class Success <T> (val data: T): ResultType<T>()
    class Error <T> (val error: ErrorType): ResultType<T>()
    class Loading <T> : ResultType <T>()

}
