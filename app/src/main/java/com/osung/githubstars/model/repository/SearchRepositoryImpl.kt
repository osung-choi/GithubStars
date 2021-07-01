package com.osung.githubstars.model.repository

import com.osung.githubstars.model.datasource.SearchDataSource
import com.osung.githubstars.repository.SearchRepository
import com.osung.githubstars.repository.entity.User
import com.osung.githubstars.repository.entity.mapper
import io.reactivex.rxjava3.core.Single

class SearchRepositoryImpl(
    private val remote: SearchDataSource //Remote 에서 유저 검색 접근
): SearchRepository {
    override fun searchUserName(query: String): Single<List<User>> {
        return remote.requestSearchUser(query).map {
            it.mapper()
        }
    }
}