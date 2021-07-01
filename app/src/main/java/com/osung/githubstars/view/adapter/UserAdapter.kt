package com.osung.githubstars.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.osung.githubstars.databinding.ItemHeaderBinding
import com.osung.githubstars.databinding.ItemUserBinding
import com.osung.githubstars.repository.entity.User

class UserAdapter(
    private val listener: OnUserItemClickListener
): ListAdapter<UserDataList, RecyclerView.ViewHolder>(UserDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when(viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder(ItemHeaderBinding.inflate(inflater, parent, false))
            else -> UserViewHolder(ItemUserBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is HeaderViewHolder -> holder.bind(getItem(position) as UserDataList.Header)
            is UserViewHolder -> holder.bind(getItem(position) as UserDataList.UserData)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is UserDataList.Header -> ITEM_VIEW_TYPE_HEADER
            is UserDataList.UserData -> ITEM_VIEW_TYPE_ITEM
        }
    }

    /**
     * 마지막으로 등록된 헤더 알파벳과 비교하여 달라졌을 경우 헤더 추가
     *
     * @param users 유저 리스트
     */
    fun submitHeaderList(users: List<User>) {
        var lastHeader: Char? = null
        val dataList = mutableListOf<UserDataList>()

        users.forEach {
            val alphabet = it.login.first() //유저 네임의 첫 글자 얻어오기.

            //헤더 정보가 없거나(최초) 마지막으로 등록한 헤더와 알파벳이 달라졌을 경우.
            if(lastHeader == null || lastHeader != alphabet) {
                lastHeader = alphabet

                dataList.add(UserDataList.Header(alphabet)) //헤더 추가.
            }

            dataList.add(UserDataList.UserData(it))
        }

        submitList(dataList)
    }

    inner class UserViewHolder(
        private val binding: ItemUserBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(userData: UserDataList.UserData) {
            binding.userData = userData

            //유저 리스트 클릭 이벤트(즐겨찾기 상태 변경)
            binding.root.setOnClickListener {
                listener.setFavoriteStateChange(userData.user)
            }
        }
    }

    inner class HeaderViewHolder(
        private val binding: ItemHeaderBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(header: UserDataList.Header) {
            binding.headerData = header
        }
    }

    companion object {
        const val ITEM_VIEW_TYPE_HEADER = 0
        const val ITEM_VIEW_TYPE_ITEM = 1
    }
}