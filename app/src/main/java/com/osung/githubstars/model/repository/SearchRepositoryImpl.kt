package com.osung.githubstars.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.osung.githubstars.model.datasource.FavoriteDataSource
import com.osung.githubstars.model.datasource.SearchDataSource
import com.osung.githubstars.repository.SearchRepository
import com.osung.githubstars.repository.entity.User
import com.osung.githubstars.repository.entity.mapper
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class SearchRepositoryImpl(
    private val remote: SearchDataSource, //Remote 에서 유저 검색 접근
    private val local: FavoriteDataSource //Local 에서 즐겨찾기 데이터 접근
): SearchRepository {
    override fun searchUserName(query: String): Single<List<User>> {
        return remote.requestSearchUser(query).map { it.mapper() }
    }

    override fun selectFavoriteUserAllList(): LiveData<List<User>> {
        return local.selectFavoriteUserAllList().map { result -> result.map { it.mapper() } }
    }

    override fun insertFavoriteUser(user: User): Completable {
        return local.insertFavoriteUser(user.mapper())
    }

    override fun deleteFavoriteUser(user: User): Completable {
        return local.deleteFavoriteUser(user.mapper())
    }
}