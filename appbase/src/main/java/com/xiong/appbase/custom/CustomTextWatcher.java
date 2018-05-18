package com.xiong.appbase.custom;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by iiMedia on 2018/5/9.
 * 可以少些两个不经常用的方法
 */

public class CustomTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
