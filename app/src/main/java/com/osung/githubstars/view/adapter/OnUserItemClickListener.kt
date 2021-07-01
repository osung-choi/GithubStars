package com.osung.githubstars.view.adapter

import com.osung.githubstars.repository.entity.User

interface OnUserItemClickListener {
    fun setFavoriteStateChange(user: User)
}