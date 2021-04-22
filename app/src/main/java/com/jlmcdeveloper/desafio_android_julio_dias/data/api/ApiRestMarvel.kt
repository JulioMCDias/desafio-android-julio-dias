package com.jlmcdeveloper.desafio_android_julio_dias.data.api

import com.jlmcdeveloper.desafio_android_julio_dias.data.api.model.CharactersPayload
import com.jlmcdeveloper.desafio_android_julio_dias.data.api.model.HQPayload
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiRestMarvel {
    @GET("/v1/public/characters")
    fun listCharacters(@Query("apikey") apikey: String,
                       @Query("ts") ts: Int,
                       @Query("hash") hash: String,
                       @Query("offset") perPage: Int,
                       @Query("limit") page: Int):
            Call<CharactersPayload>


    @GET("/v1/public/characters/{characterId}/comics")
    fun listHQs(@Path("characterId") user: String,
                @Query("apikey") apikey: String,
                @Query("ts") ts: Int,
                @Query("hash") hash: String):
            Call<HQPayload>
}