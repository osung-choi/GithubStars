package com.osung.githubstars.di

import com.osung.githubstars.model.datasource.FavoriteDataSource
import com.osung.githubstars.model.datasource.SearchDataSource
import com.osung.githubstars.model.local.LocalFavoriteDataSourceImpl
import com.osung.githubstars.model.remote.RemoteSearchDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    @Singleton
    fun provideSearchDataSource(impl: RemoteSearchDataSourceImpl): SearchDataSource

    @Binds
    @Singleton
    fun provideFavoriteDataSource(impl: LocalFavoriteDataSourceImpl): FavoriteDataSource
}