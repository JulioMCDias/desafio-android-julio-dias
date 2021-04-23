package com.jlmcdeveloper.desafio_android_julio_dias.di

import com.jlmcdeveloper.desafio_android_julio_dias.data.RepositoryCharacter
import com.jlmcdeveloper.desafio_android_julio_dias.data.RepositoryHQ
import com.jlmcdeveloper.desafio_android_julio_dias.data.api.ApiEndPoint
import com.jlmcdeveloper.desafio_android_julio_dias.data.api.ApiRestMarvel
import com.jlmcdeveloper.desafio_android_julio_dias.data.source.MarvelDataSource
import com.jlmcdeveloper.desafio_android_julio_dias.data.source.MarvelDataSourceImpl
import com.jlmcdeveloper.desafio_android_julio_dias.data.model.Character
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


// ------------------------ api --------------------------
val remoteDataSourceModule = module {
    factory { providesOkHttpClient() }
    single { createWebService<ApiRestMarvel>(
        okHttpClient = get(),
        url = ApiEndPoint.url
    ) }

    factory<MarvelDataSource> { MarvelDataSourceImpl(apiRest = get()) }
}

fun providesOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient , url: String): T {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(url)
        .client(okHttpClient)
        .build()
        .create(T::class.java)
}



// ------------------------ repository --------------------------
val repositoryModule = module {
    single(named("character")) { Character() }

    factory{ RepositoryCharacter(get() as MarvelDataSource, get(named("character"))) }
    factory{ RepositoryHQ(get() as MarvelDataSource, get(named("character"))) }
}



val appModules = remoteDataSourceModule + repositoryModule