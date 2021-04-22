package com.jlmcdeveloper.desafio_android_julio_dias.ui.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.jlmcdeveloper.desafio_android_julio_dias.data.ImageAspectRatio
import com.jlmcdeveloper.desafio_android_julio_dias.data.ImageSize
import com.jlmcdeveloper.desafio_android_julio_dias.data.model.Character
import com.jlmcdeveloper.desafio_android_julio_dias.databinding.ItemCharacterBinding
import com.jlmcdeveloper.desafio_android_julio_dias.databinding.ItemEmptyBinding
import com.jlmcdeveloper.desafio_android_julio_dias.databinding.ItemLoadingBinding
import com.jlmcdeveloper.desafio_android_julio_dias.ui.baseholder.BaseViewHolder
import com.jlmcdeveloper.desafio_android_julio_dias.ui.baseholder.EmptyHolder
import com.jlmcdeveloper.desafio_android_julio_dias.ui.baseholder.LoadingHolder
import com.jlmcdeveloper.desafio_android_julio_dias.ui.character.CharacterActivity
import com.squareup.picasso.Picasso

class RepositoryAdapter(
    private val viewModel: MainViewModel,
    owner: LifecycleOwner
) : RecyclerView.Adapter<BaseViewHolder<Character>>() {

    private val VIEW_TYPE_EMPTY = 0
    private val VIEW_TYPE_NORMAL = 1
    private val VIEW_TYPE_ITEM_LOADING = 2

    private var listData: MutableList<Character> = mutableListOf()

    init{
        viewModel.listData.observe(owner, Observer{
            if(it.isNotEmpty()) {
                listData = it
                notifyDataSetChanged()
            }
        })
        viewModel.loadingNewItems.observe(owner, Observer {
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

            VIEW_TYPE_ITEM_LOADING -> LoadingHolder(ItemLoadingBinding.inflate(inflater, parent, false))

            else -> EmptyHolder(
                { viewModel.updateList() }, ItemEmptyBinding.inflate(inflater, parent, false))
        }
    }

    override fun getItemCount(): Int {
        if(viewModel.loadingList.value!! || listData.isNotEmpty())
            return (listData.size + if(viewModel.loadingNewItems.value!!) 1 else 0)
        return 1    // item ViewHolder empty
    }


    override fun getItemViewType(position: Int): Int {
        if(viewModel.loadingList.value!! || listData.isNotEmpty()) {
            return if (listData.size > position)
                VIEW_TYPE_NORMAL
            else
                VIEW_TYPE_ITEM_LOADING
        }
        return VIEW_TYPE_EMPTY
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Character>, position: Int) {
        if(listData.size <= position || !viewModel.loadingList.value!! && listData.isEmpty())
            holder.bind(null)
        else
            holder.bind(listData[position])
    }




    //====================== ViewHolder Repository =============================
    class CharacterHolder(
        private val setCharacter: (Character)-> Unit,
        private val context: Context,
        private val binding: ItemCharacterBinding) : BaseViewHolder<Character>(binding.root) {

        override fun bind(item: Character?) {
            binding.character = item
            item?.let {
                Picasso.get()
                    .load(it.getUrlImage(ImageAspectRatio.STANDARD, ImageSize.XLARGE))
                    .into(binding.imageUser)
            }


            binding.executePendingBindings()

            // ----- abrir activity -----
            binding.cardViewRepository.setOnClickListener {
                if (item != null) {
                    setCharacter(item)
                }
                context.startActivity(Intent(context, CharacterActivity::class.java))
            }
        }
    }
}