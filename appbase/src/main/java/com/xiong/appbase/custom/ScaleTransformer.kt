package com.xiong.appbase.custom

import android.support.v4.view.ViewPager
import android.view.View

/**
 * Created by iiMedia on 2018/4/24.
 * ViewPager切换,放大缩放动画
 */
class ScaleTransformer : ViewPager.PageTransformer {
    private val MIN_SCALE = 0.75f

    override fun transformPage(page: View, position: Float) {
//        val pageWidth = page.width
        val scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position))

        //只缩放Y轴
        if (position < 0) { // [-1,0]
            // Scale the page down (between MIN_SCALE and 1)
//            page.scaleX = scaleFactor
            page.scaleY = scaleFactor

        } else if (position == 0f) {
//            page.scaleX = 1f
            page.scaleY = 1f

        } else if (position <= 1) { // (0,1]
            // Scale the page down (between MIN_SCALE and 1)
//            page.scaleX = scaleFactor
            page.scaleY = scaleFactor

        }
    }
}