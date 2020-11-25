package com.xiong.appbase.utils;

import android.widget.Toast;

import com.xiong.appbase.base.BaseApplication;

public class ToastUtil {
    public static void showToast(String text) {
        Toast.makeText(BaseApplication.getAppContext(), text, Toast.LENGTH_LONG).show();
    }
}
