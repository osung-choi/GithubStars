package com.osung.githubstars.model.local.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteUserObject(
    @PrimaryKey val id: Int,
    val avatarUrl: String,
    val login: String,
)