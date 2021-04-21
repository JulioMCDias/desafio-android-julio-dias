package com.jlmcdeveloper.desafio_android_julio_dias.ui.character

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jlmcdeveloper.desafio_android_julio_dias.R
import com.jlmcdeveloper.desafio_android_julio_dias.databinding.ActivityCharacterBinding
import com.jlmcdeveloper.desafio_android_julio_dias.databinding.ActivityMainBinding
import com.jlmcdeveloper.desafio_android_julio_dias.ui.main.MainViewModel
import com.jlmcdeveloper.desafio_android_julio_dias.ui.main.RepositoryAdapter
import org.koin.android.ext.android.inject

class CharacterActivity : AppCompatActivity() {
    private val viewModel: CharacterViewModel by inject()
    lateinit var binding: ActivityCharacterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_character)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setSupportActionBar(binding.toolbarCharacter)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //------ botão para carregar a tela de hq mais cara --------
        binding.btnSave.setOnClickListener {
            this.startActivity(Intent(this, CharacterActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.load()
        viewModel.setImage(binding.imageView)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }
}