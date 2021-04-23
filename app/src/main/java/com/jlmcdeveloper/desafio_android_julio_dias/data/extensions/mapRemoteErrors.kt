package com.jlmcdeveloper.desafio_android_julio_dias.data.extensions

import com.jlmcdeveloper.desafio_android_julio_dias.data.response.ResultRemote
import retrofit2.HttpException

fun Throwable.mapRemoteErrors(): ResultRemote.ErrorResponse {
    return when (this) {
        is HttpException -> {
            when (code()) {
                401, 403 -> ResultRemote.ErrorResponse.TokenExpired(this)
                409 -> ResultRemote.ErrorResponse.EmptyParameter(this)
                else -> ResultRemote.ErrorResponse.Unknown(this)
            }
        }
        else -> ResultRemote.ErrorResponse.Unknown(this)
    }
}