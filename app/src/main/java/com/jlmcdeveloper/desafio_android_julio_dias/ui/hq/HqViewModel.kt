package com.jlmcdeveloper.desafio_android_julio_dias.ui.hq

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jlmcdeveloper.desafio_android_julio_dias.data.ImageAspectRatio
import com.jlmcdeveloper.desafio_android_julio_dias.data.ImageSize
import com.jlmcdeveloper.desafio_android_julio_dias.data.RepositoryHQ
import com.jlmcdeveloper.desafio_android_julio_dias.data.model.HQ
import com.jlmcdeveloper.desafio_android_julio_dias.data.response.ResultRequired
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HqViewModel (private val repository: RepositoryHQ) : ViewModel() {
    val loadingHQ = MutableLiveData(false)
    val btnUpdate = MutableLiveData(false)
    val txtEmpty = MutableLiveData(false)
    val message = MutableLiveData<String>()
    val title = MutableLiveData<String>()
    val typePrice = MutableLiveData<String>()
    val price = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val image = MutableLiveData<String>()



    //--------- carregamento incial ---------
    fun load(){
        loadListHQs()
    }


    private fun loadListHQs() {
        loading(true)

        viewModelScope.launch(Dispatchers.IO) {
            repository.getHQ().collect {
                loading(false)
                when (it) {
                    is ResultRequired.Success ->
                        setInfo(it.result)

                    is ResultRequired.Error -> {
                        btnUpdate.postValue(true)
                        txtEmpty.postValue(true)
                        message.postValue("erro:"+ (it.throwable?.message ?: "desconhecido"))
                    }
                }
            }
        }

    }

    // -- carregar a HQ --
    private fun setInfo(hq: HQ) {
        if(hq.title.isNullOrBlank())
            title.postValue("Não possui HQ")
        else {
            title.postValue(hq.title)
            description.postValue(hq.description)
            price.postValue(hq.price.toString())
            typePrice.postValue(hq.typePrice + ": ")
            image.postValue(hq.getUrlImage(ImageAspectRatio.PORTRAIT, ImageSize.UNCANNY))
        }
    }

    private fun loading(value: Boolean){
        if(value){
            loadingHQ.postValue(true)
            btnUpdate.postValue(false)
            txtEmpty.postValue(false)
        }else {
            loadingHQ.postValue(false)
        }
    }
}