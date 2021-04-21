package com.jlmcdeveloper.desafio_android_julio_dias.data.model

import android.widget.ImageView
import com.jlmcdeveloper.desafio_android_julio_dias.data.ImageAspectRatio
import com.jlmcdeveloper.desafio_android_julio_dias.data.ImageSize
import com.jlmcdeveloper.desafio_android_julio_dias.data.api.model.CharactersPayload
import com.squareup.picasso.Picasso

data class Character(
    var id : String,
    var name: String,
    var description: String,
    var urlImage: String,
    var extension: String
    ){

    constructor(character: CharactersPayload.DataPayload.Characters) : this (
        id = character.Id,
        name = character.name,
        description = character.description,
        urlImage = character.image.path+"/",
        extension = "."+character.image.extension
    )
    constructor() : this("","", "", "", "")

    fun setImage(imageView: ImageView, aspectRatio: ImageAspectRatio, size: ImageSize) {
        Picasso.get()
                .load(urlImage + aspectRatio.aspectRatio + size.size+ extension)
                .into(imageView)
    }
}
