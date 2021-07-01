package com.osung.githubstars.model.remote

import com.osung.githubstars.model.remote.data.ResponseSearchUser
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("/search/users")
    fun requestSearchUserName(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Single<ResponseSearchUser>

    companion object {
        const val baseUrl = "https://api.github.com"
    }
}