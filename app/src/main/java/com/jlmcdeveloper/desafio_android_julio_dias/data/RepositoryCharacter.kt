package com.jlmcdeveloper.desafio_android_julio_dias.data

import com.jlmcdeveloper.desafio_android_julio_dias.data.api.ApiEndPoint
import com.jlmcdeveloper.desafio_android_julio_dias.data.mapper.AndroidCharacterMapper
import com.jlmcdeveloper.desafio_android_julio_dias.data.source.MarvelDataSource
import com.jlmcdeveloper.desafio_android_julio_dias.data.model.Character
import com.jlmcdeveloper.desafio_android_julio_dias.data.response.ResultRemote
import com.jlmcdeveloper.desafio_android_julio_dias.data.response.ResultRequired
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class RepositoryCharacter(private val marvelDataSource: MarvelDataSource, val character: Character) {
    val listCharacters = mutableListOf<Character>()


    private fun getPage(): Int{
        return (listCharacters.size / ApiEndPoint.perPage) + 1
    }



    fun getCharacters(): Flow<ResultRequired<List<Character>>> {
        return flow{
            val response = getCharacterRemote()
            if(response is ResultRequired.Success)
                listCharacters.addAll(response.result)
            emit(response)
        }
    }

    fun setCharacter(character1: Character){
        this.character.apply {
            character.id = character1.id
            character.name = character1.name
            character.urlImage = character1.urlImage
            character.description = character1.description
            character.extension = character1.extension
        }
    }



    private suspend fun getCharacterRemote(): ResultRequired<List<Character>> {

        return when(val resultRemote = marvelDataSource.listCharacter(getPage())) {
            is ResultRemote.Success -> {
                ResultRequired.Success(
                    result = AndroidCharacterMapper.map(resultRemote.response)
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
}