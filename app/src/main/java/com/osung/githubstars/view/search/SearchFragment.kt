package com.osung.githubstars.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.osung.githubstars.databinding.SearchFragmentBinding
import com.osung.githubstars.repository.entity.User
import com.osung.githubstars.view.adapter.OnUserItemClickListener
import com.osung.githubstars.view.adapter.UserAdapter
import com.osung.githubstars.view.main.MainViewModel
import com.osung.githubstars.view.utils.KeyboardUtil
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(), OnUserItemClickListener {
    private lateinit var binding: SearchFragmentBinding
    private val viewModel: SearchViewModel by viewModel()
    private val parentViewModel: MainViewModel by sharedViewModel() //Activity ViewModel

    private val adapter by lazy { UserAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.searchApiList.adapter = adapter
        binding.searchApiList.layoutManager = LinearLayoutManager(requireContext())

        //액티비티 뷰모델의 즐겨찾기 목록을 관찰하여 자신의 뷰모델에게 전달.
        parentViewModel.favoriteUserAllList.observe(viewLifecycleOwner, {
            viewModel.refreshFavoriteUserAllList(it)
        })

        //키보드 감추기 및 EditText 포커스 제거
        viewModel.keyboardHide.observe(viewLifecycleOwner, {
            KeyboardUtil.hideKeyboard(requireContext(), binding.inputSearchQuery)
        })
    }

    companion object {
        fun newInstance() = SearchFragment()
    }

    /**
     * 즐겨찾기 상태 변경(기존의 상태를 검사하여 추가 또는 삭제)
     * 검색과 즐겨찾기 탭에서 즐겨찾기 상태 변경이 일어날 수 있으므로
     * 액티비티 뷰모델에 메서드 정의.
     *
     * @param user 즐겨찾기 상태 변경할 유저 데이터
     */
    override fun setFavoriteStateChange(user: User) {
        if(user.isFavorite) {
            parentViewModel.deleteFavoriteUser(user)
        }else {
            parentViewModel.insertFavoriteUser(user)
        }
    }
}