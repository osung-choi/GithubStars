package com.osung.githubstars.view.favorite

import androidx.lifecycle.*
import com.osung.githubstars.repository.entity.User
import io.reactivex.rxjava3.disposables.CompositeDisposable

class FavoriteViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _favoriteUserAllList = MutableLiveData<List<User>>() //즐겨찾기 전체 리스트 관찰자
    private val _selectQuery = MutableLiveData<String>() //즐겨찾기 검색 단어 관찰자

    //즐겨찾기 전체 리스트 관찰자 및 검색 단어 관찰자 결합. (즐겨찾기 갱신 시 검색 필터 적용)
    val searchFavoriteUserList: LiveData<List<User>> = MediatorLiveData<List<User>>().apply {
        addSource(_favoriteUserAllList) { value = filterFavoriteUserList() }
        addSource(_selectQuery) { value = filterFavoriteUserList() }
    }

    //검색 완료 시 키보드 Hide 관찰자
    val keyboardHide: LiveData<Any> = Transformations.switchMap(searchFavoriteUserList) {
        MutableLiveData<Any>().apply { value = Any() }
    }

    /**
     * 검색 버튼 클릭
     *
     * @param query 검색할 단어
     */
    fun btnSearchClick(query: String) {
        _selectQuery.value = query
    }

    /**
     * 즐겨찾기 전체 리스트 갱신
     *
     * @param favoriteUsers
     */
    fun refreshFavoriteUserList(favoriteUsers: List<User>) {
        _favoriteUserAllList.value = favoriteUsers //액티비티 뷰모델에서 전달.
    }

    /**
     * 즐겨찾기 전체 목록에서 검색어가 포함된 결과 도출
     *
     * @return 검색어가 포함된 결과
     */
    private fun filterFavoriteUserList(): List<User> {
        val favoriteUserList = _favoriteUserAllList.value?: listOf() //즐겨찾기 등록 된 전체 유저 리스트
        val query = _selectQuery.value?: ""

        return favoriteUserList
            .filter { it.login.contains(query) } //검색어 필터
            .sortedBy { it.login.indexOf(query) } //검색어가 앞쪽에 포함되어있는 기준으로 정렬
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }
}