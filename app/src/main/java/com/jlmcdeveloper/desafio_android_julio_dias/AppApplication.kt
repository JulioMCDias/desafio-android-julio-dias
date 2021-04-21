package com.jlmcdeveloper.desafio_android_julio_dias

import android.app.Application
import com.jlmcdeveloper.desafio_android_julio_dias.di.activityModules
import com.jlmcdeveloper.desafio_android_julio_dias.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AppApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        val listModules = activityModules + appModules

        startKoin {
            androidLogger()
            androidContext(this@AppApplication)
            modules(listModules)
        }
    }
}