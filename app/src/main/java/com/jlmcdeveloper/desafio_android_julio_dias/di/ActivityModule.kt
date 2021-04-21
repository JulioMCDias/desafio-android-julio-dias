package com.jlmcdeveloper.desafio_android_julio_dias.di

import com.jlmcdeveloper.desafio_android_julio_dias.data.RepositoryCharacter
import com.jlmcdeveloper.desafio_android_julio_dias.ui.character.CharacterViewModel
import com.jlmcdeveloper.desafio_android_julio_dias.ui.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module { viewModel { MainViewModel(get() as RepositoryCharacter) } }
val CharacterModule = module { viewModel { CharacterViewModel(get() as RepositoryCharacter) } }

val activityModules = listOf(mainModule, CharacterModule)