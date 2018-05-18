package com.xiong.appbase.extension

import android.app.Activity
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.SearchView
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.xiong.appbase.R

/**
 * Created by iiMedia on 2018/4/23.
 */
fun SearchView.hideKeyboard(activity: Activity) {
    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
}

fun SearchView.setTextStyle(textSize: Float, hintTextColor: Int, textColor: Int) {
    //获取到TextView的控件
    val textView = findViewById(R.id.search_src_text) as EditText
    //设置字体大小,单位为14sp
    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
    //设置字体颜色
    textView.setTextColor(ContextCompat.getColor(context,textColor))
    //设置提示文字颜色
    textView.setHintTextColor(ContextCompat.getColor(context,hintTextColor))
}