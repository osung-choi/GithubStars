package com.osung.githubstars.model.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.osung.githubstars.model.local.dao.FavoriteDao
import com.osung.githubstars.model.local.data.FavoriteUserObject

@Database(entities = [FavoriteUserObject::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun favoriteDao() : FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            if(INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}