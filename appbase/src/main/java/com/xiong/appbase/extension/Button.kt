package com.xiong.appbase.extension

import android.widget.Button
import com.xiong.appbase.R

/**
 * Created by iiMedia on 2018/5/12.
 */
//验证码按钮使用
fun Button.setVerify(flag: Boolean = true) {
    if (flag) {
        isEnabled = true
        setBackgroundResource(R.drawable.btn_enable_bg)
    } else {
        isEnabled = false
        setBackgroundResource(R.drawable.btn_disabled_bg)
    }
}