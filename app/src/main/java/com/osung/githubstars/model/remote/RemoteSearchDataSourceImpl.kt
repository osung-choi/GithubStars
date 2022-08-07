package com.osung.githubstars.model.remote

import com.osung.githubstars.PAGE
import com.osung.githubstars.PAGE_SIZE
import com.osung.githubstars.model.remote.data.ResponseSearchUser
import com.osung.githubstars.model.datasource.SearchDataSource
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RemoteSearchDataSourceImpl @Inject constructor(
    private val api: Api
): SearchDataSource {
    override fun requestSearchUser(query: String): Single<ResponseSearchUser> {
        val apiQuery = query.plus(" in:login")
        return api.requestSearchUserName(apiQuery, PAGE, PAGE_SIZE)
    }
}