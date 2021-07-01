package com.osung.githubstars.view.main

import androidx.lifecycle.ViewModel
import com.osung.githubstars.repository.FavoriteRepository
import com.osung.githubstars.repository.entity.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(
    private val repository: FavoriteRepository //즐겨찾기 관련 Repository
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val favoriteUserAllList = repository.selectFavoriteUserAllList() //즐겨찾기 전체 리스트 관찰자(Local)

    /**
     * 즐겨찾기 등록
     *
     * @param user 등록할 유저 데이터
     */
    fun insertFavoriteUser(user: User) {
        repository.insertFavoriteUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .run { compositeDisposable.add(this) }
    }

    /**
     * 즐겨찾기 삭제
     *
     * @param user 삭제할 유저 데이터
     */
    fun deleteFavoriteUser(user: User) {
        repository.deleteFavoriteUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .run { compositeDisposable.add(this) }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}