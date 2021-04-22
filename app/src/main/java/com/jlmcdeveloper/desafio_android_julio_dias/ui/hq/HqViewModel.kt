package com.jlmcdeveloper.desafio_android_julio_dias.ui.hq

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jlmcdeveloper.desafio_android_julio_dias.data.ImageAspectRatio
import com.jlmcdeveloper.desafio_android_julio_dias.data.ImageSize
import com.jlmcdeveloper.desafio_android_julio_dias.data.RepositoryHQ
import com.jlmcdeveloper.desafio_android_julio_dias.data.model.HQ

class HqViewModel (private val repository: RepositoryHQ) : ViewModel() {
    val loadingHQ = MutableLiveData(false)
    val message = MutableLiveData<String>()
    val title = MutableLiveData<String>()
    val typePrice = MutableLiveData<String>()
    val price = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val image = MutableLiveData<String>()




    //--------- carregamento incial ---------
    fun load(){
        loadListCharacter()
    }


    private fun error(info: String){
        message.postValue(info)
        loading(false)
    }

    private fun loadListCharacter() {
        loading(true)

        repository.getHQ({
            setInfo(it)
            loading(false)
        },{ error("erro ao carregar a HQ") })
    }

    // -- carregar a HQ --
    private fun setInfo(hq: HQ) {
        title.postValue(hq.title)
        description.postValue(hq.description)
        price.postValue(hq.price.toString())
        typePrice.postValue(hq.typePrice+": ")
        image.postValue(hq.getUrlImage(ImageAspectRatio.PORTRAIT, ImageSize.UNCANNY))
    }

    private fun loading(value: Boolean){
        if(value){
            loadingHQ.postValue(true)
        }else {
            loadingHQ.postValue(false)
        }
    }
}