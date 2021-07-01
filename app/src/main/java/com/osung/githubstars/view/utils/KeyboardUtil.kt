package com.osung.githubstars.view.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

object KeyboardUtil {
    /**
     * 키보드 내리기 및 포커스 제거
     *
     * @param context
     * @param editText
     */
    fun hideKeyboard(context: Context, editText: EditText) {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)

        editText.clearFocus()
    }
}