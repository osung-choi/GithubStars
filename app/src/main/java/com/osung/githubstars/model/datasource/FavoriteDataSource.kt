package com.osung.githubstars.model.datasource

import androidx.lifecycle.LiveData
import com.osung.githubstars.model.local.data.FavoriteUserObject
import io.reactivex.rxjava3.core.Completable

interface FavoriteDataSource {
    /**
     * 즐겨찾기 전체 목록 조회
     *
     * @return 전체 목록 관찰자
     */
    fun selectFavoriteUserAllList() : LiveData<List<FavoriteUserObject>>

    /**
     * 즐겨찾기 목록 조회
     *
     * @param query 검색어
     * @return
     */
    fun selectFavoriteUserList(query: String) : LiveData<List<FavoriteUserObject>>

    /**
     * 즐겨찾기 추가
     *
     * @param user 추가할 데이터
     * @return 성공 여부
     */
    fun insertFavoriteUser(user: FavoriteUserObject): Completable

    /**
     * 즐겨찾기 삭제
     *
     * @param user 삭제할 데이터
     * @return 성공 여부
     */
    fun deleteFavoriteUser(user: FavoriteUserObject): Completable
}