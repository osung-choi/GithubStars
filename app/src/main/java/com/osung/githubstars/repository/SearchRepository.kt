package com.osung.githubstars.repository

import androidx.lifecycle.LiveData
import com.osung.githubstars.repository.entity.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface SearchRepository {
    /**
     * 유저 네임 검색
     *
     * @param query 검색어
     * @return
     */
    fun searchUserName(query: String): Single<List<User>>
}