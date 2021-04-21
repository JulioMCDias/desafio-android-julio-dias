package com.jlmcdeveloper.desafio_android_julio_dias.data.api

import com.jlmcdeveloper.desafio_android_julio_dias.data.model.Character
import com.jlmcdeveloper.desafio_android_julio_dias.data.model.HQ

interface MarvelDataSource {
    fun listCharacter(page: Int, success : (List<Character>) -> Unit, failure: (String) -> Unit)
    fun listHQs(idCharacter: Character, success : (List<HQ>) -> Unit, failure: (String) -> Unit)
}