package com.jlmcdeveloper.desafio_android_julio_dias.data.mapper

import com.jlmcdeveloper.desafio_android_julio_dias.data.api.model.HQPayload
import com.jlmcdeveloper.desafio_android_julio_dias.data.model.HQ


object AndroidHqMapper {
    fun map(hq: HQPayload) = hq.HQPayload.results.map { map(it) }

    private fun map(hq: HQPayload.DataPayload.HQs) = HQ(
        title =hq.title,
        description = hq.description,
        price = hq.prices?.get(0)?.price,
        typePrice = hq.prices?.get(0)?.type,
        urlImage = hq.image.path+"/",
        extension = "."+hq.image.extension
    )
}