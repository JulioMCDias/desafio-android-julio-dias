package com.jlmcdeveloper.desafio_android_julio_dias.data.mapper

import com.jlmcdeveloper.desafio_android_julio_dias.data.api.model.CharactersPayload
import com.jlmcdeveloper.desafio_android_julio_dias.data.model.Character

object AndroidCharacterMapper {
    fun map(payload: CharactersPayload) = payload.CharactersPayload.results.map { map(it) }

    private fun map(character: CharactersPayload.DataPayload.Characters) = Character(
        id = character.Id,
        name = character.name,
        description = character.description,
        urlImage = character.image.path+"/",
        extension = "."+character.image.extension
    )
}