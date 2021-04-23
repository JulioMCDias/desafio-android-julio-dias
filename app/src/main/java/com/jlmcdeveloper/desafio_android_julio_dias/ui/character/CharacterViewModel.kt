package com.jlmcdeveloper.desafio_android_julio_dias.ui.character

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jlmcdeveloper.desafio_android_julio_dias.data.ImageAspectRatio
import com.jlmcdeveloper.desafio_android_julio_dias.data.ImageSize
import com.jlmcdeveloper.desafio_android_julio_dias.data.RepositoryCharacter

class CharacterViewModel(private val repository: RepositoryCharacter) : ViewModel() {
    val name = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val image = MutableLiveData<String>()

    //--------- carregamento incial ---------
    fun load(){
        name.postValue(repository.character.name)
        if(repository.character.description.isNotBlank())
            description.postValue(repository.character.description)
        else
            description.postValue("sem descrição")
        setImage()
    }

    private fun setImage(){
        image.postValue(repository.character.getUrlImage(
            ImageAspectRatio.PORTRAIT, ImageSize.UNCANNY))
    }

}