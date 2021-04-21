package com.jlmcdeveloper.desafio_android_julio_dias.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module { viewModel { MainViewModel(get() as RepositoryMain) } }

val activityModules = listOf(mainModule)