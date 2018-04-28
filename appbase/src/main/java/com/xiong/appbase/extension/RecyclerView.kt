package com.xiong.appbase.extension

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.xiong.appbase.R
import com.xiong.appbase.custom.GridDividerItemDecoration

/**
 * Created by iiMedia on 2018/4/25.
 * RecyclerView扩展方法
 */
//添加分割线
fun RecyclerView.addDivider(ctx: Context, drawable: Int, orientation: Int = LinearLayoutManager.VERTICAL) {
    val divider = DividerItemDecoration(ctx, orientation)
    divider.setDrawable(ContextCompat.getDrawable(ctx, drawable)!!)
    addItemDecoration(divider)
}

//GridLayoutManager添加分割线
fun RecyclerView.addGridDivider(ctx: Context, height: Int = 16, color: Int = R.color.white) {
    addItemDecoration(GridDividerItemDecoration(height, ContextCompat.getColor(ctx, color)))
}