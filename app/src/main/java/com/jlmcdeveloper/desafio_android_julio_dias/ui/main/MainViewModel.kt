package com.jlmcdeveloper.desafio_android_julio_dias.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jlmcdeveloper.desafio_android_julio_dias.data.RepositoryCharacter
import com.jlmcdeveloper.desafio_android_julio_dias.data.model.Character

class MainViewModel(private val repository: RepositoryCharacter) : ViewModel(){
    val listData = MutableLiveData<MutableList<Character>>(mutableListOf())
    val loadingList = MutableLiveData(false)
    val loadingNewItems = MutableLiveData(false)
    val message = MutableLiveData<String>()



    //--- referencia para buscar novas paginas -----
    fun positionList(pos: Int){
        if(!loadingList.value!! && !loadingNewItems.value!! && listData.value!!.isNotEmpty()) {
            if (listData.value!!.size <= pos+1)
                loadListCharacter()
        }
    }

    // -------- info para a activity detalhes------------
    fun setRepository(character: Character) {
        repository.setCharacter(character)
    }


    // -- carregar lista se ainda não foi carregado --
    fun updateList() {
        loadListCharacter()
    }

    //--------- carregamento incial ---------
    fun load(){
        if(repository.listCharacter.size > 0)
            listData.postValue(repository.listCharacter)
        else
            loadListCharacter()
    }



    private fun error(info: String){
        message.postValue(info)
        loading(false)
    }


    private fun loadListCharacter() {
        loading(true)

        repository.moreCharacter({
            listData.postValue(it)
            loading(false)
        },{ error("erro ao carregar a lista de Repositorios") })
    }


    private fun loading(value: Boolean){
        if(value){
            if (listData.value!!.isEmpty() || !value)
                loadingList.postValue(true)
            else
                loadingNewItems.postValue(true)
        }else {
            loadingList.postValue(false)
            loadingNewItems.postValue(false)
        }
    }
}