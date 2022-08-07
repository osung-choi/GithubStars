package com.osung.githubstars.di

import com.osung.githubstars.model.repository.FavoriteRepositoryImpl
import com.osung.githubstars.model.repository.SearchRepositoryImpl
import com.osung.githubstars.repository.FavoriteRepository
import com.osung.githubstars.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun provideSearchRepository(impl: SearchRepositoryImpl): SearchRepository

    @Binds
    @Singleton
    fun provideFavoriteRepository(impl: FavoriteRepositoryImpl): FavoriteRepository
}