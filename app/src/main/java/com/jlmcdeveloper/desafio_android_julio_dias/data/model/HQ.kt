package com.jlmcdeveloper.desafio_android_julio_dias.data.model

import com.jlmcdeveloper.desafio_android_julio_dias.data.ImageAspectRatio
import com.jlmcdeveloper.desafio_android_julio_dias.data.ImageSize

data class HQ(
    var title: String?,
    var description: String?,
    var price: Float?,
    var typePrice: String?,
    var urlImage: String,
    var extension: String
){
    constructor(): this("", "", 0f,"","","")


    fun getUrlImage(aspectRatio: ImageAspectRatio, size: ImageSize): String {
        return urlImage + aspectRatio.aspectRatio + size.size + extension
    }

}
