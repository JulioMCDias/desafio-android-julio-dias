package com.jlmcdeveloper.desafio_android_julio_dias.ui.hq

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jlmcdeveloper.desafio_android_julio_dias.R
import com.jlmcdeveloper.desafio_android_julio_dias.databinding.ActivityHqBinding
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class HqActivity : AppCompatActivity() {
    private val viewModel: HqViewModel by viewModel()
    private lateinit var binding: ActivityHqBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_hq)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupView()
    }

    private fun setupView() {
        setSupportActionBar(binding.toolbarHQ)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        // ----- image -----
        viewModel.image.observe(this ){
            Picasso.get()
                .load(it)
                .into(binding.imageView)
        }

        // ----- mensagem de erro -------
        viewModel.message.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
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