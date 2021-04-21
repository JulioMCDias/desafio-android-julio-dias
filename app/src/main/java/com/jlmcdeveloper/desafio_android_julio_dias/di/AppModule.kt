package com.jlmcdeveloper.desafio_android_julio_dias.di

import com.jlmcdeveloper.desafio_android_julio_dias.data.api.ApiEndPoint
import com.jlmcdeveloper.desafio_android_julio_dias.data.api.ApiRestMarvel
import com.jlmcdeveloper.desafio_android_julio_dias.data.api.AppMarvelDataSource
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// ------------------------ api --------------------------
val retrofitModule = module {
    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiEndPoint.url)
            .client(get() as OkHttpClient)
            .build()
    }
}
val cacheModule = module {
    val cacheSize: Long = 10 * 1024 * 1024
    single { Cache(androidContext().cacheDir, cacheSize) }
}

val apiModule = module {
    single { (get() as Retrofit).create(ApiRestMarvel::class.java) }
    single { AppMarvelDataSource(get()) }
}

val okHttpClientModule = module { single { OkHttpClient.Builder().cache(get()).build() } }


val api = listOf(cacheModule, okHttpClientModule, retrofitModule, apiModule)




// ------------------------ repository --------------------------
val repositoryModule = module {
    single(named("userRepository")) { GitCollection() }

    factory{ RepositoryMain(get() as AppGithubDataSource, get(named("userRepository"))) }
    factory{ RepositoryPullRequest(get() as AppGithubDataSource, get(named("userRepository"))) }
}



val appModules = api + repositoryModule