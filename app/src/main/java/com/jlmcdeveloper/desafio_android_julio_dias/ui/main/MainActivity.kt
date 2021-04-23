package com.jlmcdeveloper.desafio_android_julio_dias.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jlmcdeveloper.desafio_android_julio_dias.R
import com.jlmcdeveloper.desafio_android_julio_dias.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel  by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        setupView()
    }


    private fun setupView() {
        setSupportActionBar(binding.toolbarMain)

        // ----- mensagem de erro -------
        viewModel.message.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        val adapter = RepositoryAdapter(viewModel, this)
        binding.recyclerView.adapter = adapter

        // ----------notify hole------------
        viewModel.loadingList.observe(this, {
            adapter.notifyDataSetChanged()
        })

        setupRecyclerView()
    }


    private fun setupRecyclerView() = with(binding.recyclerView) {

        //------ posição da lista para o carregamento automatico --------
        this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                (binding.recyclerView.layoutManager as GridLayoutManager?)?.let {
                    viewModel.positionList(it.findLastVisibleItemPosition())
                }
            }
        })
    }


    override fun onStart() {
        super.onStart()
        viewModel.load()
    }
}