package com.osung.githubstars.model.datasource

import com.osung.githubstars.model.remote.data.ResponseSearchUser
import io.reactivex.rxjava3.core.Single

interface SearchDataSource {
    /**
     * 유저 검색 요청
     *
     * @param query 검색할 단어
     * @return 검색 결과 리스트
     */
    fun requestSearchUser(query: String): Single<ResponseSearchUser>
}