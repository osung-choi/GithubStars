package com.osung.githubstars

import android.app.Application
import com.osung.githubstars.di.dataModule
import com.osung.githubstars.di.databaseModule
import com.osung.githubstars.di.networkModule
import com.osung.githubstars.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        //Koin Load
        startKoin {
            androidContext(this@App)
            modules(networkModule, viewModelModule, databaseModule, dataModule)
        }
    }
}

const val PAGE = 1
const val PAGE_SIZE = 100