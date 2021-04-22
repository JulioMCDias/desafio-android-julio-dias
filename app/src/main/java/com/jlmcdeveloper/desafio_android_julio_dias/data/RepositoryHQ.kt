package com.jlmcdeveloper.desafio_android_julio_dias.data

import com.jlmcdeveloper.desafio_android_julio_dias.data.api.MarvelDataSource
import com.jlmcdeveloper.desafio_android_julio_dias.data.model.Character
import com.jlmcdeveloper.desafio_android_julio_dias.data.model.HQ

class RepositoryHQ(private val marvelDataSource: MarvelDataSource, private val character: Character) {

    var running = false

    fun getHQ(success : (HQ) -> Unit, failure: (String) -> Unit) {
        if(!running) {
            running = true
            marvelDataSource.listHQs(character, success = {
                success(getHQPrice(it))
                running= false
            },
                failure = {
                    failure(it)
                    running= false
                })
        }
    }

    private fun getHQPrice(hqs: List<HQ>): HQ{
        var hq = HQ()
        hqs.forEach { if(it.price?:0f > hq.price!!) hq = it}
        return hq
    }
}