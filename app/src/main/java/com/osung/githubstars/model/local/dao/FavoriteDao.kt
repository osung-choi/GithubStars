package com.osung.githubstars.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.osung.githubstars.model.local.data.FavoriteUserObject
import io.reactivex.rxjava3.core.Completable

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM FavoriteUserObject ORDER BY login")
    fun selectFavoriteUserAllList() : LiveData<List<FavoriteUserObject>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteUser(user: FavoriteUserObject) : Completable

    @Delete
    fun deleteFavoriteUser(user: FavoriteUserObject) : Completable
}