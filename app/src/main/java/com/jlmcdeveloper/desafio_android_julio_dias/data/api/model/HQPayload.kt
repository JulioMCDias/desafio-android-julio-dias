package com.jlmcdeveloper.desafio_android_julio_dias.data.api.model

import com.google.gson.annotations.SerializedName

class HQPayload(
    @SerializedName("data") val HQPayload: DataPayload) {


    class DataPayload(
        val total: Int,
        val results: List<HQs>) {

        class HQs(
            @SerializedName("id") val Id: String,
            val title: String,
            val description: String,
            val prices: String)
    }
}