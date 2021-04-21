package com.jlmcdeveloper.desafio_android_julio_dias.data.api

import com.jlmcdeveloper.desafio_android_julio_dias.data.api.model.CharactersPayload
import com.jlmcdeveloper.desafio_android_julio_dias.data.api.model.HQPayload
import com.jlmcdeveloper.desafio_android_julio_dias.data.model.Character
import com.jlmcdeveloper.desafio_android_julio_dias.data.model.HQ
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppMarvelDataSource(private val apiRest: ApiRestMarvel) : MarvelDataSource {

    override fun listCharacter(page: Int, success: (List<Character>) -> Unit,
                               failure: (String) -> Unit) {

        val call =apiRest.listCharacters(
            ApiEndPoint.apikey,
            ApiEndPoint.ts,
            ApiEndPoint.hash,
            ApiEndPoint.perPage * page, ApiEndPoint.perPage)

        call.enqueue(object : Callback<CharactersPayload> {
            override fun onResponse(call: Call<CharactersPayload>,
                                    response: Response<CharactersPayload>
            ) {

                if (response.isSuccessful) {
                    val collection = mutableListOf<Character>()
                    response.body()?.CharactersPayload?.results?.forEach { collection.add(Character(it)) }

                    if(response.body() != null)
                        success(collection)
                    else
                        failure("response.body() = null")
                }
                else
                    failure("errorBody: ${response.errorBody()}")
            }

            override fun onFailure(call: Call<CharactersPayload>, t: Throwable) {
                failure("error: onFailure: ${t.message}")
            }
        })


    }

    override fun listHQs(idCharacter: Character, success: (List<HQ>) -> Unit,
                         failure: (String) -> Unit) {

        val call =apiRest.listHQs(ApiEndPoint.apikey,
            ApiEndPoint.ts,
            ApiEndPoint.hash,
            idCharacter.id)

        call.enqueue(object : Callback<HQPayload> {
            override fun onResponse(call: Call<HQPayload>,
                                    response: Response<HQPayload>
            ) {

                if (response.isSuccessful) {
                    val collection = mutableListOf<HQ>()
                    response.body()?.HQPayload?.results?.forEach { collection.add(HQ(it)) }

                    if(response.body() != null)
                        success(collection)
                    else
                        failure("response.body() = null")
                }
                else
                    failure("errorBody: ${response.errorBody()}")
            }

            override fun onFailure(call: Call<HQPayload>, t: Throwable) {
                failure("error: onFailure: ${t.message}")
            }
        })

    }

}