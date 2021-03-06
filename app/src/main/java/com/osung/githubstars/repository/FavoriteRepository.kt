package com.osung.githubstars.repository

import androidx.lifecycle.LiveData
import com.osung.githubstars.repository.entity.User
import io.reactivex.rxjava3.core.Completable

interface FavoriteRepository {
    /**
     * 즐겨찾기 목록 조회
     *
     * @param query 검색어
     * @return
     */
    fun selectFavoriteUserList(query: String) : LiveData<List<User>>

    /**
     * 즐겨찾기 삭제
     *
     * @param user 삭제할 유저 데이터
     * @return
     */
    fun deleteFavoriteUser(user: User): Completable
}