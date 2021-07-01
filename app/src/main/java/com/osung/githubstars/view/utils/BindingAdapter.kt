package com.osung.githubstars.view.utils

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.osung.githubstars.repository.entity.User
import com.osung.githubstars.view.adapter.UserAdapter

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("app:setUserItems")
    fun setUserItems(recyclerView: RecyclerView, user: List<User>?) {
        user?.let {
            (recyclerView.adapter as? UserAdapter)?.submitHeaderList(it)
        }
    }


    @JvmStatic
    @BindingAdapter("app:setUserProfileImage")
    fun setUserProfileImage(imageView: ImageView, profileUrl: String?) {
        profileUrl?.let {
            Glide.with(imageView)
                .load(it)
                .circleCrop()
                .into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("app:onEditorSearchActionListener")
    fun setOnEditorSearchActionListener(editText: EditText, listener: () -> Unit) {
        editText.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                listener.invoke()
                true

            }else false
        }
    }

}