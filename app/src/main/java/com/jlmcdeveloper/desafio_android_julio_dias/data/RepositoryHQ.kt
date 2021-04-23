package com.jlmcdeveloper.desafio_android_julio_dias.data

import com.jlmcdeveloper.desafio_android_julio_dias.data.mapper.AndroidHqMapper
import com.jlmcdeveloper.desafio_android_julio_dias.data.source.MarvelDataSource
import com.jlmcdeveloper.desafio_android_julio_dias.data.model.Character
import com.jlmcdeveloper.desafio_android_julio_dias.data.model.HQ
import com.jlmcdeveloper.desafio_android_julio_dias.data.response.ResultRemote
import com.jlmcdeveloper.desafio_android_julio_dias.data.response.ResultRequired
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.flow

class RepositoryHQ(private val marvelDataSource: MarvelDataSource, private val character: Character) {


    fun getHQ(): Flow<ResultRequired<HQ>> {
        return flow{
            val response = getHqRemote()
            if(response is ResultRequired.Success)
                emit(getHQPrice(response.result))
            else if (response is ResultRequired.Error)
                emit(response)
        }
    }


    private suspend fun getHqRemote(): ResultRequired<List<HQ>> {
        return when(val resultRemote = marvelDataSource.listHQs(character)) {
            is ResultRemote.Success -> {
                ResultRequired.Success(
                    result = AndroidHqMapper.map(resultRemote.response)
                )
            }
            is ResultRemote.ErrorResponse.TokenExpired ->
                ResultRequired.Error(Throwable("TokenExpired"))
            is ResultRemote.ErrorResponse.EmptyParameter ->
                ResultRequired.Error(Throwable("EmptyParameter"))
            is ResultRemote.ErrorResponse ->
                ResultRequired.Error(resultRemote.throwable)

            else -> ResultRequired.Error(null)
        }
    }

    private fun getHQPrice(hqs: List<HQ>): ResultRequired<HQ>{
        var hq = HQ()
        hqs.forEach { if(it.price?:0f > hq.price!!) hq = it}
        return ResultRequired.Success(hq)
    }
}