package com.osung.githubstars.view.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.osung.githubstars.databinding.FavoriteFragmentBinding
import com.osung.githubstars.repository.entity.User
import com.osung.githubstars.view.adapter.OnUserItemClickListener
import com.osung.githubstars.view.adapter.UserAdapter
import com.osung.githubstars.view.utils.KeyboardUtil
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment(), OnUserItemClickListener {
    private lateinit var binding: FavoriteFragmentBinding
    private val viewModel: FavoriteViewModel by viewModel()

    private val adapter by lazy { UserAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FavoriteFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.searchFavoriteList.adapter = adapter


        //키보드 감추기 및 EditText 포커스 제거
        viewModel.keyboardHide.observe(viewLifecycleOwner, {
            KeyboardUtil.hideKeyboard(requireContext(), binding.inputFilterFavorite)
        })

    }

    /**
     * 즐겨찾기 상태 변경(기존의 상태를 검사하여 삭제)
     * 검색과 즐겨찾기 탭에서 즐겨찾기 상태 변경이 일어날 수 있으므로
     * 액티비티 뷰모델에 메서드 정의.
     *
     * @param user 즐겨찾기 상태 삭제할 유저 데이터
     */
    override fun setFavoriteStateChange(user: User) {
        if(user.isFavorite) {
            viewModel.deleteFavoriteUser(user)
        }
    }

    companion object {
        fun newInstance() = FavoriteFragment()
    }
}