package com.jlmcdeveloper.desafio_android_julio_dias.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jlmcdeveloper.desafio_android_julio_dias.data.RepositoryCharacter
import com.jlmcdeveloper.desafio_android_julio_dias.data.model.Character
import com.jlmcdeveloper.desafio_android_julio_dias.data.response.ResultRequired
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(private val repository: RepositoryCharacter) : ViewModel(){
    val listData = MutableLiveData<MutableList<Character>>(mutableListOf())
    val btnUpdate = MutableLiveData(false)
    val txtEmpty = MutableLiveData(false)
    val loadingList = MutableLiveData(false)
    val loadingNewItems = MutableLiveData(false)
    val message = MutableLiveData<String>()



    //--- referencia para buscar novas paginas -----
    fun positionList(pos: Int){
        if(!loadingList.value!! && !loadingNewItems.value!! && repository.listCharacters.isNotEmpty()) {
            if (repository.listCharacters.size <= pos+1)
                loadListCharacter()
        }
    }

    // -------- info para a activity detalhes------------
    fun setRepository(character: Character) {
        repository.setCharacter(character)
    }


    // -- carregar lista se ainda não foi carregada --
    fun updateList() {
        loadListCharacter()
    }

    //--------- carregamento incial ---------
    fun load(){
        if(repository.listCharacters.size > 0)
            listData.postValue(repository.listCharacters)
        else
            loadListCharacter()
    }



    private fun loadListCharacter() {
        loading(true)

        viewModelScope.launch(Dispatchers.IO) {
            repository.getCharacters().collect {
                loading(false)
                when (it) {
                    is ResultRequired.Success -> {
                        if ((it.result).isNotEmpty())
                            listData.postValue(repository.listCharacters)
                    }

                    is ResultRequired.Error -> {
                        btnUpdate.postValue(true)
                        message.postValue("erro:"+ (it.throwable?.message ?: "desconhecido"))
                    }
                }
            }
        }
    }


    private fun loading(value: Boolean){
        if(value){
            if (repository.listCharacters.isEmpty() || !value) {
                loadingList.postValue(true)
                btnUpdate.postValue(false)
                txtEmpty.postValue(false)
            }else
                loadingNewItems.postValue(true)
        }else {
            loadingList.postValue(false)
            loadingNewItems.postValue(false)
            if(repository.listCharacters.isEmpty()) {
                txtEmpty.postValue(true)
            }
        }
    }
}