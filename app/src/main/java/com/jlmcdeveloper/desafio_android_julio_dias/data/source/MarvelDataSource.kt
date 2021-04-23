package com.jlmcdeveloper.desafio_android_julio_dias.data.source

import com.jlmcdeveloper.desafio_android_julio_dias.data.api.model.CharactersPayload
import com.jlmcdeveloper.desafio_android_julio_dias.data.api.model.HQPayload
import com.jlmcdeveloper.desafio_android_julio_dias.data.response.ResultRemote
import com.jlmcdeveloper.desafio_android_julio_dias.data.model.Character

interface MarvelDataSource {
    suspend fun listCharacter(page: Int): ResultRemote<CharactersPayload>
    suspend fun listHQs(idCharacter: Character): ResultRemote<HQPayload>
}