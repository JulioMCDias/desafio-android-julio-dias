package com.jlmcdeveloper.desafio_android_julio_dias.data.model

import com.jlmcdeveloper.desafio_android_julio_dias.data.api.model.HQPayload

data class HQ(
    var title: String,
    var description : String,
    var price: String
){
    constructor(hq: HQPayload.DataPayload.HQs) : this(
        title =hq.title,
        description = hq.description,
        price = hq.prices
    )

    constructor(): this(title="", description="", price="")
}
