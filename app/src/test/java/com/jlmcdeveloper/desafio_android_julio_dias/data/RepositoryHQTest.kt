package com.jlmcdeveloper.desafio_android_julio_dias.data

import com.jlmcdeveloper.desafio_android_julio_dias.coroutinerule.CoroutineTestRule
import com.jlmcdeveloper.desafio_android_julio_dias.data.api.ApiEndPoint
import com.jlmcdeveloper.desafio_android_julio_dias.data.api.model.HQPayload
import com.jlmcdeveloper.desafio_android_julio_dias.data.model.Character
import com.jlmcdeveloper.desafio_android_julio_dias.data.response.ResultRemote
import com.jlmcdeveloper.desafio_android_julio_dias.data.response.ResultRequired
import com.jlmcdeveloper.desafio_android_julio_dias.data.source.MarvelDataSource
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule

class RepositoryHQTest : TestCase() {
    private lateinit var repositoryHQ: RepositoryHQ
    
    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()


    @ExperimentalCoroutinesApi
    fun testGetHQ_Success() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val marvelDataSource: MarvelDataSource = mockk()
        coEvery { marvelDataSource.listHQs(Character()) } returns createHQPayload()
        val character = Character()
        repositoryHQ = RepositoryHQ(marvelDataSource, character)

        // ---- buscar primeira pagina  ------
        repositoryHQ.getHQ().collect {
            when (it) {
                is ResultRequired.Success -> {
                    assertTrue("title errado", it.result.title == "title19")
                    assertTrue("description errado", it.result.description == "description19")
                    assertTrue("urlImage errado", it.result.urlImage == "path19/")
                    assertTrue("extension errado", it.result.extension == ".extension19")
                    assertTrue("price errado", it.result.price == 19f)
                    assertTrue("typePrice errado", it.result.typePrice == "type1")
                }
                else -> assertTrue("Resultado erro", false)
            }
        }
    }
    

    private fun createHQPayload() : ResultRemote<HQPayload> {
        val hq = mutableListOf<HQPayload.DataPayload.HQs>()
        for( i in 0 until ApiEndPoint.perPage) {
            val prices = mutableListOf<HQPayload.DataPayload.HQs.Price>()
            prices.add(HQPayload.DataPayload.HQs.Price("type1", i.toFloat()))
            prices.add(HQPayload.DataPayload.HQs.Price("type2", i.toFloat()+1))
            hq.add(HQPayload.DataPayload.HQs(
                    "$i", "title$i", "description$i",
                    prices, HQPayload.DataPayload.HQs.Thumbnail("path$i", "extension$i")
                )
            )
        }
        val charactersPayload = HQPayload(
            HQPayload.DataPayload(ApiEndPoint.perPage, hq))
        return ResultRemote.Success(charactersPayload)
    }
}