package com.jlmcdeveloper.desafio_android_julio_dias.data.response

sealed class ResultRequired<out T> {
    data class Success<out T>(val result: T): ResultRequired<T>()
    data class Error(val throwable: Throwable?): ResultRequired<Nothing>()
}