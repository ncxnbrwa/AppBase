package com.xiong.appbase.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xiong.appbase.R;
import com.xiong.appbase.base.BaseActivity;
import com.xiong.appbase.base.BaseApplication;
import com.xiong.appbase.base.Config;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;


/**
 * Created by iiMedia on 2017/12/14.
 * 常用工具类
 */

public class MyUtils {
    public static final String TAG = "MyUtils";
    public static Toast mToast;

    //检测某APP是否安装
    public static boolean isAppInstalled(Context context, String packageName) {
        boolean isInstall = false;
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packageInfoList = pm.getInstalledPackages(PackageManager.GET_META_DATA);
        for (PackageInfo info : packageInfoList) {
            if (info.packageName.equalsIgnoreCase(packageName)) {
                isInstall = true;
            }
        }
        return isInstall;
    }

    //跳转微信
    public static void intentToWechat(BaseActivity activity) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.setComponent(new ComponentName(Config.WEXIN_PACKAGE,
                Config.WEXIN_HOME_CLASS));
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivityForResult(intent, 0);
    }

    //格式化为百分数
    public static String formatted2Percentage(float decimal) {
        //获取格式化对象
        NumberFormat nt = NumberFormat.getPercentInstance();
        //设置百分数精确度2即保留几位小数
        nt.setMinimumFractionDigits(2);
        return nt.format(decimal);
    }

    //流式布局item
    public static TextView createRelatedItem(Context ctx, String text) {
        TextView relatedItem = new TextView(ctx);
        relatedItem.setBackgroundResource(R.drawable.float_item_bg);
        relatedItem.setTextSize(13f);
        relatedItem.setText(text);
        relatedItem.setTextColor(ContextCompat.getColor(ctx, R.color.text_lable_jb));
        relatedItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return relatedItem;
    }

    //获取imei标识
    public static String getImei(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }
        TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    //保留两个小数
    public static String formatDouble2(double d) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(d);
    }

    public static int getScreenWidth() {
        return BaseApplication.getAppContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return BaseApplication.getAppContext().getResources().getDisplayMetrics().heightPixels;
    }

    public static int dp2px(final float dpValue) {
        final float scale = BaseApplication.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static void showToast(String msg) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(BaseApplication.getAppContext(), msg, Toast.LENGTH_SHORT);
        mToast.show();
    }


    //检查网络是否可用
    public static boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) BaseApplication.getAppContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        return !(networkinfo == null || !networkinfo.isAvailable());
    }

}
