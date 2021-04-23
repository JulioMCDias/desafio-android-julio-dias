package com.jlmcdeveloper.desafio_android_julio_dias.data.source

import com.jlmcdeveloper.desafio_android_julio_dias.data.api.ApiEndPoint
import com.jlmcdeveloper.desafio_android_julio_dias.data.api.ApiRestMarvel
import com.jlmcdeveloper.desafio_android_julio_dias.data.extensions.mapRemoteErrors
import com.jlmcdeveloper.desafio_android_julio_dias.data.api.model.CharactersPayload
import com.jlmcdeveloper.desafio_android_julio_dias.data.api.model.HQPayload
import com.jlmcdeveloper.desafio_android_julio_dias.data.response.ResultRemote
import com.jlmcdeveloper.desafio_android_julio_dias.data.model.Character

class MarvelDataSourceImpl(private val apiRest: ApiRestMarvel) : MarvelDataSource {

    override suspend fun listCharacter(page: Int): ResultRemote<CharactersPayload> {
        return try {
            val characters = apiRest.listCharacters(
                ApiEndPoint.apikey,
                ApiEndPoint.ts,
                ApiEndPoint.hash,
                ApiEndPoint.perPage * page, ApiEndPoint.perPage
            )

            ResultRemote.Success(response = characters)
        } catch (throwable: Throwable) {
            throwable.mapRemoteErrors()
        }
    }

    override suspend fun listHQs(idCharacter: Character): ResultRemote<HQPayload> {
        return try {
            val hq = apiRest.listHQs(idCharacter.id,
                ApiEndPoint.apikey,
                ApiEndPoint.ts,
                ApiEndPoint.hash
            )

            ResultRemote.Success(response = hq)
        } catch (throwable: Throwable) {
            throwable.mapRemoteErrors()
        }
    }

}