package com.jlmcdeveloper.desafio_android_julio_dias.data.api.model

import com.google.gson.annotations.SerializedName


class CharactersPayload(
    @SerializedName("data") val CharactersPayload: DataPayload) {


    class DataPayload(
        val total: Int,
        val results: List<Characters>) {

        class Characters(
            @SerializedName("id") val Id: String,
            val name: String,
            @SerializedName("thumbnail") val image: Thumbnail){

            class Thumbnail(val path : String, val extension : String)
        }
    }
}
