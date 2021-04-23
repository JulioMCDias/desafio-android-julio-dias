package com.jlmcdeveloper.desafio_android_julio_dias.di

import com.jlmcdeveloper.desafio_android_julio_dias.data.RepositoryCharacter
import com.jlmcdeveloper.desafio_android_julio_dias.data.RepositoryHQ
import com.jlmcdeveloper.desafio_android_julio_dias.ui.character.CharacterViewModel
import com.jlmcdeveloper.desafio_android_julio_dias.ui.hq.HqViewModel
import com.jlmcdeveloper.desafio_android_julio_dias.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module { viewModel { MainViewModel(get() as RepositoryCharacter) } }
val CharacterModule = module { viewModel { CharacterViewModel(get() as RepositoryCharacter) } }
val HQModule = module { viewModel { HqViewModel(get() as RepositoryHQ) } }

val activityModules = listOf(mainModule, CharacterModule, HQModule)