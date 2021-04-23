package com.jlmcdeveloper.desafio_android_julio_dias.data

import com.jlmcdeveloper.desafio_android_julio_dias.coroutinerule.CoroutineTestRule
import com.jlmcdeveloper.desafio_android_julio_dias.data.api.ApiEndPoint
import com.jlmcdeveloper.desafio_android_julio_dias.data.api.model.CharactersPayload
import com.jlmcdeveloper.desafio_android_julio_dias.data.model.Character
import com.jlmcdeveloper.desafio_android_julio_dias.data.response.ResultRemote
import com.jlmcdeveloper.desafio_android_julio_dias.data.response.ResultRequired
import com.jlmcdeveloper.desafio_android_julio_dias.data.source.MarvelDataSource
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule

class RepositoryCharacterTest : TestCase() {
    private lateinit var repositoryCharacter: RepositoryCharacter

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()


    @ExperimentalCoroutinesApi
    fun testGetCharacter_twoPage_Success() = coroutinesTestRule.testDispatcher.runBlockingTest{
        var pageOne = true
        val marvelDataSource: MarvelDataSource = mockk()
        coEvery { marvelDataSource.listCharacter(0) } returns createCharactersPayload()
        coEvery { marvelDataSource.listCharacter(1) } returns createCharactersPayload()
        val character = Character()
        repositoryCharacter = RepositoryCharacter(marvelDataSource, character)

        assert(repositoryCharacter.listCharacters.isEmpty())

        // ---- buscar primeira pagina  ------
        repositoryCharacter.getCharacters().collect{
            when(it){
                is ResultRequired.Success ->{
                    if(pageOne) {
                        assertTrue("lista vazia", it.result.isNotEmpty())
                        assertTrue("quantidade incorreta", it.result.size == ApiEndPoint.perPage)
                        assertTrue("id errado", it.result[0].id == "0")
                        assertTrue("name errado", it.result[0].name == "name0")
                        assertTrue("description errado", it.result[0].description == "description0")
                        assertTrue("urlImage errado", it.result[0].urlImage == "path0/")
                        assertTrue("extension errado", it.result[0].extension == ".extension0")
                    }else{
                        assertTrue("quantidade incorreta",it.result.size == ApiEndPoint.perPage*2)
                        assertTrue("id errado",it.result[ApiEndPoint.perPage].id == "0")
                        assertTrue("name errado",it.result[ApiEndPoint.perPage].name == "name0")
                        assertTrue("description errado",it.result[ApiEndPoint.perPage].description == "description0")
                        assertTrue("urlImage errado",it.result[ApiEndPoint.perPage].urlImage == "path0/")
                        assertTrue("extension errado",it.result[ApiEndPoint.perPage].extension == ".extension0")
                    }
                }
                else -> assertTrue("Resultado erro",false)
            }
            pageOne = false
        }


        // ---- buscar segunda pagina  ------
        repositoryCharacter.getCharacters().collect{
            when(it){
                is ResultRequired.Success ->{

                }
                else -> assertTrue("Resultado erro",false)
            }
        }
    }


    @ExperimentalCoroutinesApi
    fun testGetCharacter_onePage_Success() = coroutinesTestRule.testDispatcher.runBlockingTest{
        val marvelDataSource: MarvelDataSource = mockk()
        coEvery { marvelDataSource.listCharacter(0) } returns createCharactersPayload()
        val character = Character()
        repositoryCharacter = RepositoryCharacter(marvelDataSource, character)

        assertTrue("lista não vazia",repositoryCharacter.listCharacters.isEmpty())

        // ---- buscar primeira pagina  ------
        repositoryCharacter.getCharacters().collect{
            when(it){
                is ResultRequired.Success ->{
                    assertTrue("lista vazia",it.result.isNotEmpty())
                    assertTrue("quantidade incorreta",it.result.size == ApiEndPoint.perPage)
                    assertTrue("id errado",it.result[0].id == "0")
                    assertTrue("name errado",it.result[0].name == "name0")
                    assertTrue("description errado",it.result[0].description == "description0")
                    assertTrue("urlImage errado",it.result[0].urlImage == "path0/")
                    assertTrue("extension errado",it.result[0].extension == ".extension0")
                }
                else -> assertTrue("Resultado erro",false)
            }
        }
    }

    private fun createCharactersPayload() : ResultRemote<CharactersPayload>{
        val gC = mutableListOf<CharactersPayload.DataPayload.Characters>()
        for( i in 0 until ApiEndPoint.perPage)
            gC.add(CharactersPayload.DataPayload.Characters("$i","name$i", "description$i",
                CharactersPayload.DataPayload.Characters.Thumbnail("path$i","extension$i")
            ))
        val charactersPayload = CharactersPayload(
            CharactersPayload.DataPayload(ApiEndPoint.perPage, gC))
        return ResultRemote.Success(charactersPayload)
    }
}