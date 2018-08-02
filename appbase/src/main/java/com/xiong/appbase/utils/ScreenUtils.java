package com.xiong.appbase.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import com.xiong.appbase.base.BaseApplication;

/**
 * Created by iiMedia on 2018/8/2.
 * 尺寸及适配工具集合
 */

public class ScreenUtils {

    //+0.5f防止出现为0的情况
    public static int dp2px(float dpValue) {
        final float scale = BaseApplication.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dp(float pxValue) {
        final float scale = BaseApplication.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getScreenWidth() {
        return BaseApplication.getAppContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return BaseApplication.getAppContext().getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    //判断是否横屏
    public static boolean isLandscape() {
        return BaseApplication.getAppContext().getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    //判断是否竖屏
    public static boolean isPortrait() {
        return BaseApplication.getAppContext().getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT;
    }


    //适配竖屏滑动
    public static void adaptScreen4VerticalSlide(final Activity activity,
                                                 final int designWidthInDp) {
        adaptScreen(activity, designWidthInDp, true);
    }

    //适配横屏滑动
    public static void adaptScreen4HorizontalSlide(final Activity activity,
                                                   final int designHeightInDp) {
        adaptScreen(activity, designHeightInDp, false);
    }

    //取消适配
    public static void cancelAdaptScreen(final Activity activity) {
        final DisplayMetrics appDm = BaseApplication.getAppContext().getResources().getDisplayMetrics();
        final DisplayMetrics activityDm = activity.getResources().getDisplayMetrics();
        activityDm.density = appDm.density;
        activityDm.scaledDensity = appDm.scaledDensity;
        activityDm.densityDpi = appDm.densityDpi;
    }


    //具体适配代码,摘自blankj的工具类
    private static void adaptScreen(final Activity activity,
                                    final float sizeInDp,
                                    final boolean isVerticalSlide) {
        final DisplayMetrics appDm = BaseApplication.getAppContext().getResources().getDisplayMetrics();
        final DisplayMetrics activityDm = activity.getResources().getDisplayMetrics();
        if (isVerticalSlide) {
            activityDm.density = activityDm.widthPixels / sizeInDp;
        } else {
            activityDm.density = activityDm.heightPixels / sizeInDp;
        }
        //字体缩放比
        activityDm.scaledDensity = activityDm.density * (appDm.scaledDensity / appDm.density);
        activityDm.densityDpi = (int) (160 * activityDm.density);
    }

}
