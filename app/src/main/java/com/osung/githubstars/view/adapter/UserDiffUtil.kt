package com.osung.githubstars.view.adapter

import androidx.recyclerview.widget.DiffUtil

class UserDiffUtil: DiffUtil.ItemCallback<UserDataList>() {
    override fun areItemsTheSame(oldDataList: UserDataList, newDataList: UserDataList): Boolean {
        return oldDataList.id == newDataList.id
    }

    override fun areContentsTheSame(oldDataList: UserDataList, newDataList: UserDataList): Boolean {
        return oldDataList == newDataList
    }
}