package com.jlmcdeveloper.desafio_android_julio_dias.ui.character

import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jlmcdeveloper.desafio_android_julio_dias.data.ImageAspectRatio
import com.jlmcdeveloper.desafio_android_julio_dias.data.ImageSize
import com.jlmcdeveloper.desafio_android_julio_dias.data.RepositoryCharacter

class CharacterViewModel(private val repository: RepositoryCharacter) : ViewModel() {
    val name = MutableLiveData<String>()
    val description = MutableLiveData<String>()

    //--------- carregamento incial ---------
    fun load(){
        name.postValue(repository.character.name)
        description.postValue(repository.character.description)

    }

    fun setImage(image: ImageView){
        repository.character.setImage(image, ImageAspectRatio.PORTRAIT, ImageSize.UNCANNY)
    }

}