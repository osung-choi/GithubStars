package com.osung.githubstars.view.favorite

import androidx.lifecycle.*
import com.osung.githubstars.repository.FavoriteRepository
import com.osung.githubstars.repository.entity.User
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: FavoriteRepository
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _selectQuery = MutableLiveData<String>() //즐겨찾기 검색 단어 관찰자

    val searchFavoriteUserList = Transformations.switchMap(_selectQuery) { //즐겨찾기 검색 결과 관찰자
        repository.selectFavoriteUserList(it) //검색어를 포함하는 즐겨찾기 리스트 관찰자 반환
    }

    //검색 완료 시 키보드 Hide 관찰자
    val keyboardHide: LiveData<Any> = Transformations.switchMap(searchFavoriteUserList) {
        MutableLiveData<Any>().apply { value = Any() }
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

    /**
     * 검색 버튼 클릭
     *
     * @param query 검색할 단어
     */
    fun btnSearchClick(query: String) {
        if(query.isNotBlank())
            _selectQuery.value = query
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }
}