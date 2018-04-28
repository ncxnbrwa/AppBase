package com.xiong.appbase.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xiong.appbase.R;
import com.xiong.appbase.base.BaseActivity;
import com.xiong.appbase.base.Config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.List;


/**
 * Created by iiMedia on 2017/12/14.
 * 常用工具类
 */

public class MyUtils {
    public static final String TAG = "MyUtils";

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


    //键盘是否显示
    public static boolean isKeyboardShown(View rootView) {
        final int softKeyboardHeight = 100;
        Rect frame = new Rect();
        //获取root在窗体的可视区域
        rootView.getWindowVisibleDisplayFrame(frame);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        //获取不可见区域高度
        int heightDiff = rootView.getBottom() - frame.bottom;
        DLog.w(TAG, "屏幕高度:" + rootView.getBottom() + ", 可见区域高度:" + frame.bottom + ", " +
                "键盘高度:" + heightDiff);
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

    //格式化为百分数
    public static String formatted2Percentage(float decimal) {
        //获取格式化对象
        NumberFormat nt = NumberFormat.getPercentInstance();
        //设置百分数精确度2即保留几位小数
        nt.setMinimumFractionDigits(2);
        return nt.format(decimal);
    }

    //MD5加密
    public static String encryptMD5(String text) {
        StringBuilder sb = new StringBuilder();
        try {
            byte[] textByte = MessageDigest.getInstance("MD5").digest(text.getBytes());
            for (byte b : textByte) {
                if ((b & 0xFF) < 0x10) {
                    sb.append(b);
                }
                sb.append(Integer.toHexString(b & 0xFF));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    // 根据字符串生成密钥24位的字节数组
    public static byte[] build3Deskey(String keyStr) throws Exception {
        byte[] key = new byte[24];
        byte[] temp = keyStr.getBytes("UTF-8");
        if (key.length > temp.length) {
            System.arraycopy(temp, 0, key, 0, temp.length);

        } else {
            System.arraycopy(temp, 0, key, 0, key.length);

        }
        return key;
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
}
