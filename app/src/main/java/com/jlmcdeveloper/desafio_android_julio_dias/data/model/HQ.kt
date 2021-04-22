package com.jlmcdeveloper.desafio_android_julio_dias.data.model

import com.jlmcdeveloper.desafio_android_julio_dias.data.ImageAspectRatio
import com.jlmcdeveloper.desafio_android_julio_dias.data.ImageSize
import com.jlmcdeveloper.desafio_android_julio_dias.data.api.model.HQPayload

data class HQ(
    var title: String?,
    var description: String?,
    var price: Float?,
    var typePrice: String?,
    var urlImage: String,
    var extension: String
){
    constructor(hq: HQPayload.DataPayload.HQs) : this (
        title =hq.title,
        description = hq.description,
        price = hq.prices?.get(0)?.price,
        typePrice = hq.prices?.get(0)?.type,
        urlImage = hq.image.path+"/",
        extension = "."+hq.image.extension)

    constructor(): this("", "", 0f,"","","")


    fun getUrlImage(aspectRatio: ImageAspectRatio, size: ImageSize): String {
        return urlImage + aspectRatio.aspectRatio + size.size + extension
    }

}
