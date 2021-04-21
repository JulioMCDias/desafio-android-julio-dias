package com.jlmcdeveloper.desafio_android_julio_dias.data

import com.jlmcdeveloper.desafio_android_julio_dias.data.api.ApiEndPoint
import com.jlmcdeveloper.desafio_android_julio_dias.data.api.MarvelDataSource
import com.jlmcdeveloper.desafio_android_julio_dias.data.model.Character

class RepositoryCharacter(private val marvelDataSource: MarvelDataSource, val character: Character) {

    val listCharacter = mutableListOf<Character>()
    var running = false

    fun moreCharacter(success : (MutableList<Character>) -> Unit, failure: (String) -> Unit) {
        if(!running) {
            running = true

            marvelDataSource.listCharacter(getPage(), success = {
                listCharacter.addAll(it)
                success(listCharacter)
                running= false

            }, failure = {
                failure(it)
                running = false
            })
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

    private fun getPage(): Int{
        return (listCharacter.size / ApiEndPoint.perPage) + 1
    }
}