package com.osung.githubstars.view.search

import androidx.lifecycle.*
import com.osung.githubstars.repository.FavoriteRepository
import com.osung.githubstars.repository.SearchRepository
import com.osung.githubstars.repository.entity.User
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

/**
 * Api 검색 및 즐겨찾기 여부 검사
 *
 * @property searchRepository
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository, //검색 관련 Repository
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _searchUserList = MutableLiveData<List<User>>() //UserName 검색 결과 관찰자
    private val _favoriteUserAllList = favoriteRepository.selectFavoriteUserAllList() //즐겨찾기 전체 목록 관찰자

    //UserName 검색 결과 관찰자, 즐겨찾기 전체 목록 관찰자 결합. (이벤트에 따라 데이터 갱신)
    val searchUserList: LiveData<List<User>> = MediatorLiveData<List<User>>().apply {
        addSource(_searchUserList) { value = checkSearchUserFavoriteState() }
        addSource(_favoriteUserAllList) { value = checkSearchUserFavoriteState() }
    }

    //검색 완료 시 키보드 Hide 관찰자
    val keyboardHide: LiveData<Any> = Transformations.switchMap(searchUserList) {
        MutableLiveData<Any>().apply { value = Any() }
    }

    /**
     * 검색 버튼 클릭
     *
     * @param query 검색할 단어
     */
    fun btnSearchClick(query: String) {
        if(query.isNotBlank()) {
            searchRepository.searchUserName(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    _searchUserList.value = result
                }, {})
                .run { compositeDisposable.add(this) }
        }
    }

    /**
     * 즐겨찾기 등록
     *
     * @param user 등록할 유저 데이터
     */
    fun insertFavoriteUser(user: User) {
        favoriteRepository.insertFavoriteUser(user)
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
        favoriteRepository.deleteFavoriteUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .run { compositeDisposable.add(this) }
    }

    /**
     * 즐겨찾기 등록 여부에 따라 검색된 유저의 즐겨찾기 상태 변경
     *
     * @return
     */
    private fun checkSearchUserFavoriteState(): List<User> {
        val searchUserList = _searchUserList.value?.toMutableList() ?: mutableListOf()
        val favoriteUserList = _favoriteUserAllList.value ?: mutableListOf()

        searchUserList.forEachIndexed { index, user ->
            val isFavorite = favoriteUserList.map { it.id }.contains(user.id) //즐겨찾기 목록에서 같은 id 존재하는지 체크

            if (user.isFavorite != isFavorite) //기존의 유저 목록의 상태와 다르다면 즐겨찾기 상태 갱신
                searchUserList[index] = user.copy(isFavorite = isFavorite)
        }

        return searchUserList.sortedBy { it.login } //유저네임 기준으로 정렬
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }

}