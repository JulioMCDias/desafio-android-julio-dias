package com.jlmcdeveloper.desafio_android_julio_dias.ui.hq

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jlmcdeveloper.desafio_android_julio_dias.R
import com.jlmcdeveloper.desafio_android_julio_dias.databinding.ActivityHqBinding
import com.jlmcdeveloper.desafio_android_julio_dias.databinding.ActivityMainBinding
import com.jlmcdeveloper.desafio_android_julio_dias.ui.main.MainViewModel
import com.jlmcdeveloper.desafio_android_julio_dias.ui.main.RepositoryAdapter
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject

class HqActivity : AppCompatActivity() {
    private val viewModel: HqViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityHqBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_hq)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setSupportActionBar(binding.toolbarHQ)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // ----- mensagem de erro -------
        viewModel.message.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        // ----- image HQ -----
        viewModel.image.observe(this ){
            Picasso.get()
                .load(it)
                .into(binding.imageView)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.load()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }
}