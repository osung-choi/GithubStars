package com.osung.githubstars.repository

import androidx.lifecycle.LiveData
import com.osung.githubstars.repository.entity.User
import io.reactivex.rxjava3.core.Completable

interface FavoriteRepository {
    /**
     * 즐겨찾기 전체 목록 조회
     *
     * @return 전체 목록 관찰자
     */
    fun selectFavoriteUserAllList() : LiveData<List<User>>

    /**
     * 즐겨찾기 추가
     *
     * @param user 추가할 유저 데이터
     * @return
     */
    fun insertFavoriteUser(user: User): Completable

    /**
     * 즐겨찾기 삭제
     *
     * @param user 삭제할 유저 데이터
     * @return
     */
    fun deleteFavoriteUser(user: User): Completable
}