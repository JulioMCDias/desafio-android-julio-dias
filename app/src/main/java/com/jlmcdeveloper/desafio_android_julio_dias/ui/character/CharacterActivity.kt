package com.jlmcdeveloper.desafio_android_julio_dias.ui.character

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jlmcdeveloper.desafio_android_julio_dias.R
import com.jlmcdeveloper.desafio_android_julio_dias.databinding.ActivityCharacterBinding
import com.jlmcdeveloper.desafio_android_julio_dias.ui.hq.HqActivity
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterActivity : AppCompatActivity() {
    private val viewModel: CharacterViewModel by viewModel()
    lateinit var binding: ActivityCharacterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_character)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupView()
    }



    private fun setupView() {
        setSupportActionBar(binding.toolbarCharacter)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //------ botão para carregar activity hq --------
        binding.btnHQUpValue.setOnClickListener {
            this.startActivity(Intent(this, HqActivity::class.java))
        }

        // ----- image -----
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