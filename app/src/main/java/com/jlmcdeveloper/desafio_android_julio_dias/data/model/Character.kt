package com.jlmcdeveloper.desafio_android_julio_dias.data.model

import com.jlmcdeveloper.desafio_android_julio_dias.data.api.model.CharactersPayload

data class Character(
    var id : String,
    var name: String,
    var urlImage: String
    ){

    constructor(character: CharactersPayload.DataPayload.Characters) : this (
        id = character.Id,
        name = character.name,
        urlImage = character.image.path
    )
}
