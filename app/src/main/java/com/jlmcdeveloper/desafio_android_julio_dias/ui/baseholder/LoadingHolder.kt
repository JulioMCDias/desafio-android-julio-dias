package com.jlmcdeveloper.desafio_android_julio_dias.ui.baseholder

import com.jlmcdeveloper.desafio_android_julio_dias.databinding.ItemLoadingBinding


//====================== ViewHolder Loading =============================
class LoadingHolder<T>( binding: ItemLoadingBinding) :
    BaseViewHolder<T>(binding.root) {

    override fun bind(item: T) { }
}