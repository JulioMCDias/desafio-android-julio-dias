package com.jlmcdeveloper.desafio_android_julio_dias.data.model

import com.jlmcdeveloper.desafio_android_julio_dias.data.ImageAspectRatio
import com.jlmcdeveloper.desafio_android_julio_dias.data.ImageSize

data class Character(
    var id : String,
    var name: String,
    var description: String,
    var urlImage: String,
    var extension: String
    ){

    constructor() : this("","", "", "", "")


    fun getUrlImage(aspectRatio: ImageAspectRatio, size: ImageSize): String {
        return urlImage + aspectRatio.aspectRatio + size.size+ extension
    }
}
