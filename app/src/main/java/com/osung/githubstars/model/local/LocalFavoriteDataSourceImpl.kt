package com.osung.githubstars.model.local

import androidx.lifecycle.LiveData
import com.osung.githubstars.model.datasource.FavoriteDataSource
import com.osung.githubstars.model.local.data.FavoriteUserObject
import com.osung.githubstars.model.local.database.AppDatabase
import io.reactivex.rxjava3.core.Completable

class LocalFavoriteDataSourceImpl(
    private val database: AppDatabase
): FavoriteDataSource {
    override fun selectFavoriteUserAllList(): LiveData<List<FavoriteUserObject>> {
        return database.favoriteDao().selectFavoriteUserAllList()
    }

    override fun selectFavoriteUserList(query: String): LiveData<List<FavoriteUserObject>> {
        return database.favoriteDao().selectFavoriteUserList("%$query%")
    }

    override fun insertFavoriteUser(user: FavoriteUserObject): Completable {
        return database.favoriteDao().insertFavoriteUser(user)
    }

    override fun deleteFavoriteUser(user: FavoriteUserObject): Completable {
        return database.favoriteDao().deleteFavoriteUser(user)
    }
}