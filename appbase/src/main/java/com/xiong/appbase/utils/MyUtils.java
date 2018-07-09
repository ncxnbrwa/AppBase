package com.xiong.appbase.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
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
    public static TextView createRelatedItem(Context ctx, String text, View.OnClickListener clickListener) {
        TextView relatedItem = new TextView(ctx);
        relatedItem.setBackgroundResource(R.drawable.float_item_bg);
        relatedItem.setTextSize(13f);
        relatedItem.setText(text);
        relatedItem.setTextColor(ContextCompat.getColor(ctx, R.color.text_lable_jb));
        relatedItem.setOnClickListener(clickListener);
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

    //形如001
    public static String formatPosition3(int i) {
        DecimalFormat df = new DecimalFormat("#000");
        return df.format(i);
    }

    //形如01
    public static String formatPosition2(int i) {
        DecimalFormat df = new DecimalFormat("#00");
        return df.format(i);
    }

    public static int getScreenWidth() {
        return BaseApplication.getAppContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return BaseApplication.getAppContext().getResources().getDisplayMetrics().heightPixels;
    }

    public static int dp2px(float dpValue) {
        final float scale = BaseApplication.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dp(float pxValue) {
        final float scale = BaseApplication.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
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

    //修改某段文字中指定位置的文本色
    public static SpannableString getSpannableForeStr(String key, String msg) {
        SpannableString ss = new SpannableString(msg);
        int index = msg.indexOf(key);
        if (index >= 0) {
            ss.setSpan(new ForegroundColorSpan(Color.parseColor("#C69F73")), index,
                    index + key.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }

    /**
     * EditText竖直方向是否可以滚动
     *
     * @param editText 需要判断的EditText
     * @return true：可以滚动   false：不可以滚动
     */
    public static boolean canVerticalScroll(EditText editText) {
        //改编自View的canScrollVertically方法
        //用if (et_message.canScrollVertically(-1) || et_message.canScrollVertically(0)) {}
        //替代即可

        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() - editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        return scrollDifference != 0 && ((scrollY > 0) || (scrollY < scrollDifference - 1));
    }

    //获取状态栏高度
    public static int getStatusBarHeight(Activity activity) {
        int statusBarHeight = 0;
        if (activity != null) {
            int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
            statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    //跳转京东APP商品详情
    public static void gotoJdAppGoods(Context context, String id) {
        String uri = "openapp.jdmobile://virtual?params={\"sourceValue\":\"0_productDetail_97\",\"des\":\"productDetail\",\"skuId\":\"" +
                id +
                "\",\"category\":\"jump\",\"sourceType\":\"PCUBE_CHANNEL\"}";
        intentWithUri(context, uri);
    }

    //跳转京东APP店铺详情
    public static void gotoJdAppShop(Context context, String shopId) {
        String uri = "openApp.jdMobile://virtual?params={\"category\":\"jump\",\"des\":\"jshopMain\",\"shopId\":\"" +
                shopId + "\",\"sourceType\":\"M_sourceFrom\",\"sourceValue\":\"dp\"}";
        intentWithUri(context, uri);
    }

    //跳转天猫APP商品详情
    public static void gotoTmAppGoods(Context context, String id) {
        String uri = "tmall://tmallclient/?{\"action\":”item:id=" +
                id +
                "”}";
        intentWithUri(context, uri);
    }

    //跳转天猫APP店铺
    public static void gotoTmAppShop(Context context, String shopId) {
        String uri = "tmall://page.tm/shop?shopId=" + shopId;
        intentWithUri(context, uri);
    }

    public static void intentWithUri(Context context, String uri) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uri));
        context.startActivity(intent);
        DLog.w(TAG, "intentWithUri:" + uri);
    }

    //判断是否在主线程
    public static boolean isOnMainThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }
}
