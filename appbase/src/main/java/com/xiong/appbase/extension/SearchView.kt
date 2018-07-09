package com.xiong.appbase.extension

import android.support.v4.content.ContextCompat
import android.support.v7.widget.SearchView
import android.util.TypedValue
import android.view.Gravity
import com.xiong.appbase.R

/**
 * Created by iiMedia on 2018/4/23.
 */
fun SearchView.setTextStyle(textSize: Float, hintTextColor: Int, textColor: Int) {
    //获取到TextView的控件
    val textView = findViewById<SearchView.SearchAutoComplete>(R.id.search_src_text)
    //设置字体大小,单位为sp
    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
    textView.gravity = Gravity.CENTER_VERTICAL
    //设置字体颜色
    textView.setTextColor(ContextCompat.getColor(context, textColor))
    //设置提示文字颜色
    textView.setHintTextColor(ContextCompat.getColor(context, hintTextColor))
}