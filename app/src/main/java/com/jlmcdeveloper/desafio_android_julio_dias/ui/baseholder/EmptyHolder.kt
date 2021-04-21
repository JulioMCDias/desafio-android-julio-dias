package com.jlmcdeveloper.desafio_android_julio_dias.ui.baseholder

import com.jlmcdeveloper.desafio_android_julio_dias.databinding.ItemEmptyBinding


class EmptyHolder<T>(private val loadList: ()-> Unit, private val binding: ItemEmptyBinding) :
    BaseViewHolder<T>(binding.root) {

    override fun bind(item: T?) {
        binding.btnUpdate.setOnClickListener { loadList() }
    }
}