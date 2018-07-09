package com.xiong.appbase.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.xiong.appbase.base.BaseApplication;

/**
 * Created by iiMedia on 2018/5/17.
 * 键盘工具类
 */

public class KeyboardUtils {
    //显示键盘
    public static void showSoftInput(final Activity activity) {
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.requestFocus();
        }
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    //关闭键盘
    public static void hideSoftInput(final Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        View view = activity.getCurrentFocus();
        if (view == null) view = new View(activity);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //键盘开关
    public static void toggleSoftInput() {
        InputMethodManager imm = (InputMethodManager) BaseApplication.getAppContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    //键盘是否显示
    public static boolean isKeyboardShown(View rootView) {
        final int softKeyboardHeight = 100;
        Rect frame = new Rect();
        //获取root在窗体的可视区域
        rootView.getWindowVisibleDisplayFrame(frame);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        //获取不可见区域高度
        int heightDiff = rootView.getBottom() - frame.bottom;
//        DLog.w(TAG, "屏幕高度:" + rootView.getBottom() + ", 可见区域高度:" + frame.bottom + ", " +
//                "键盘高度:" + heightDiff);
        return heightDiff > softKeyboardHeight * dm.density;
    }

    // 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
    public static boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            //在窗口中计算这个视图的坐标。参数必须是两个整数的数组。方法返回后，数组包含该View的x和y位置
            v.getLocationInWindow(l);
            //计算出View的上下左右位置
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            //当事件位于View范围外时返回true
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    //点击空白处收起键盘,需要用的地方复制过去
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            View view = getCurrentFocus();
//            if (isShouldHideKeyboard(view, ev)) {
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context
//                        .INPUT_METHOD_SERVICE);
//                try {
//                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return super.dispatchTouchEvent(ev);
//    }
    //Kotlin写法
//    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
//        if (ev?.action == MotionEvent.ACTION_DOWN) {
//            val view = currentFocus
//            if (KeyboardUtils.isShouldHideKeyboard(view, ev)) {
//                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                try {
//                    imm.hideSoftInputFromWindow(view.windowToken, 0);
//                } catch (e: Exception) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return super.dispatchTouchEvent(ev);
//    }

}
