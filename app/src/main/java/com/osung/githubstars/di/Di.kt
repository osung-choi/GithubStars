package com.osung.githubstars.di

import com.osung.githubstars.model.remote.Api
import com.osung.githubstars.model.datasource.FavoriteDataSource
import com.osung.githubstars.model.datasource.SearchDataSource
import com.osung.githubstars.model.local.LocalFavoriteDataSourceImpl
import com.osung.githubstars.model.local.database.AppDatabase
import com.osung.githubstars.model.remote.RemoteSearchDataSourceImpl
import com.osung.githubstars.model.repository.FavoriteRepositoryImpl
import com.osung.githubstars.model.repository.SearchRepositoryImpl
import com.osung.githubstars.repository.FavoriteRepository
import com.osung.githubstars.repository.SearchRepository
import com.osung.githubstars.view.favorite.FavoriteViewModel
import com.osung.githubstars.view.search.SearchViewModel
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel { FavoriteViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}

val dataModule = module {
    single<SearchRepository> { SearchRepositoryImpl(get(), get()) }
    single<FavoriteRepository> { FavoriteRepositoryImpl(get()) }

    single<SearchDataSource> { RemoteSearchDataSourceImpl(get()) }
    single<FavoriteDataSource> { LocalFavoriteDataSourceImpl(get()) }
}

val databaseModule = module {
    single { AppDatabase.getDatabase(androidContext()) }
}

val networkModule = module {
    single {
        OkHttpClient.Builder().apply {
            addInterceptor(Interceptor { chain ->
                chain.proceed(chain.request().newBuilder().apply {
                    header("accept", "application/vnd.github.v3+json")
                }.build())
            })
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build()
    }

    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Api.baseUrl)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(get())
            .build()
            .create(Api::class.java)
    }
}