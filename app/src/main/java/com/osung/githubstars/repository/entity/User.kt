package com.osung.githubstars.repository.entity

data class User(
    val id: Int,
    val avatarUrl: String,
    val login: String,
    val isFavorite: Boolean
)