package com.osung.githubstars.repository.entity

import com.osung.githubstars.model.local.data.FavoriteUserObject
import com.osung.githubstars.model.remote.data.ResponseSearchUser

/**
 * Mapper
 *
 */

fun ResponseSearchUser.mapper(): List<User> {
    return responseUsers.map { User(it.id, it.avatarUrl, it.login, false) }
}

fun FavoriteUserObject.mapper(): User {
    return User(id, avatarUrl, login, true)
}

fun User.mapper(): FavoriteUserObject {
    return FavoriteUserObject(id, avatarUrl, login)
}