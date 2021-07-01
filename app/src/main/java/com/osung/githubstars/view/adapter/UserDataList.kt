package com.osung.githubstars.view.adapter

import com.osung.githubstars.repository.entity.User

sealed class UserDataList {
    abstract val id: Int
    data class UserData(var user: User): UserDataList() {
        override val id: Int
            get() = user.id
    }

    //알파벳 Header
    data class Header(val alphabet: Char): UserDataList() {
        override val id: Int
            get() = Int.MIN_VALUE
    }
}