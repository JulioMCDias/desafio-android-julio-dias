package com.jlmcdeveloper.desafio_android_julio_dias.ui.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.jlmcdeveloper.desafio_android_julio_dias.data.ImageAspectRatio
import com.jlmcdeveloper.desafio_android_julio_dias.data.ImageSize
import com.jlmcdeveloper.desafio_android_julio_dias.data.model.Character
import com.jlmcdeveloper.desafio_android_julio_dias.databinding.ItemCharacterBinding
import com.jlmcdeveloper.desafio_android_julio_dias.databinding.ItemLoadingBinding
import com.jlmcdeveloper.desafio_android_julio_dias.ui.baseholder.BaseViewHolder
import com.jlmcdeveloper.desafio_android_julio_dias.ui.baseholder.LoadingHolder
import com.jlmcdeveloper.desafio_android_julio_dias.ui.character.CharacterActivity
import com.squareup.picasso.Picasso

class RepositoryAdapter(
    private val viewModel: MainViewModel,
    owner: LifecycleOwner
) : RecyclerView.Adapter<BaseViewHolder<Character>>() {

    private val VIEW_TYPE_NORMAL = 0
    private val VIEW_TYPE_ITEM_LOADING = 1

    private var listData: MutableList<Character> = mutableListOf()

    init{
        viewModel.listData.observe(owner, {
            if(it.isNotEmpty()) {
                listData = it
                notifyDataSetChanged()
            }
        })
        viewModel.loadingNewItems.observe(owner, {
            if(!it)
                notifyItemRemoved(listData.size)
        })
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Character> {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        return when(viewType){
            VIEW_TYPE_NORMAL -> CharacterHolder(
                { viewModel.setRepository(it) }, context, ItemCharacterBinding.inflate(inflater, parent, false))

            else -> LoadingHolder(ItemLoadingBinding.inflate(inflater, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return (listData.size + if(viewModel.loadingNewItems.value!!) 1 else 0)
    }


    override fun getItemViewType(position: Int): Int {
        return if (listData.size > position)
            VIEW_TYPE_NORMAL
        else
            VIEW_TYPE_ITEM_LOADING

    }

    override fun onBindViewHolder(holder: BaseViewHolder<Character>, position: Int) {
        if(listData.size <= position)
            holder.bind(listData[listData.size-1])
        else
            holder.bind(listData[position])
    }




    //====================== ViewHolder Repository =============================
    class CharacterHolder(
        private val setCharacter: (Character)-> Unit,
        private val context: Context,
        private val binding: ItemCharacterBinding) : BaseViewHolder<Character>(binding.root) {

        override fun bind(item: Character) {
            binding.character = item
            Picasso.get()
                .load(item.getUrlImage(ImageAspectRatio.STANDARD, ImageSize.XLARGE))
                .into(binding.imageUser)


            binding.executePendingBindings()

            // ----- abrir activity -----
            binding.cardViewRepository.setOnClickListener {
                setCharacter(item)

                context.startActivity(Intent(context, CharacterActivity::class.java))
            }
        }
    }
}