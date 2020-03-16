package com.xiong.appbase.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xiong.appbase.R;
import com.xiong.appbase.base.BaseActivity;
import com.xiong.appbase.base.BaseApplication;
import com.xiong.appbase.base.Config;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by xiong on 2017/12/14.
 * 常用工具类
 */

public class MyUtils {
    public static final String TAG = "MyUtils";
    public static Toast mToast;
    public static final int MEDIA_TAKE_PHOTO = 1;
    public static final int MEDIA_CHOOSE_PICTURE = 2;

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
        intent.setComponent(new ComponentName(Config.WEXIN_PACKAGE, Config.WEXIN_HOME_CLASS));
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
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
        relatedItem.setTextSize(12f);
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
        DLog.w(TAG, "intentWithUri:" + uri);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uri));
        context.startActivity(intent);
    }

    //判断是否在主线程
    public static boolean isOnMainThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    //打开相机
    public static Uri openCamera(Activity mActivity, String imgName) {
        Uri imageUri;
        File imageFile = new File(mActivity.getExternalCacheDir(), imgName);
        try {
            if (imageFile.exists()) {
                imageFile.delete();
            }
            imageFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT < 24) {
            imageUri = Uri.fromFile(imageFile);
        } else {
            imageUri = FileProvider.getUriForFile(mActivity, "com.aspire.andcloudbackup.provider", imageFile);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        mActivity.startActivityForResult(intent, MEDIA_TAKE_PHOTO);
        return imageUri;
    }

    //打开相册
    public static void openAlbum(Activity mActivity) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        mActivity.startActivityForResult(intent, MEDIA_CHOOSE_PICTURE);
    }

    /**
     * @param uri       图片对应Uri
     * @param selection 图片查找条件
     * @return 图片真实路径
     */
    public static String getImagePath(Activity mActivity, Uri uri, String selection) {
        String path = "";
        Cursor cursor = mActivity.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 处理相册图片uri
     *
     * @param mActivity 上下文
     * @param data      返回的intent数据
     * @return 返回最终图片地址
     */
    public static String handleImagePath(Activity mActivity, Intent data) {
        String imagePath = "";
        if (Build.VERSION.SDK_INT >= 19) {
            Uri uri = data.getData();
            if (DocumentsContract.isDocumentUri(mActivity, uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                if (uri.getAuthority().equals("com.android.providers.media.documents")) {
                    String selection = MediaStore.Images.Media._ID + "=" + docId.split(":")[1];
                    imagePath = getImagePath(mActivity, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                } else if (uri.getAuthority().equals("com.android.providers.downloads.documents")) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse
                            ("content://downloads/public_downloads"), Long.valueOf(docId));
                    imagePath = getImagePath(mActivity, contentUri, null);
                }
            } else if (uri.getScheme().equals("content")) {
                imagePath = getImagePath(mActivity, uri, null);
            } else if (uri.getScheme().equals("file")) {
                imagePath = uri.getPath();
            }
        } else {
            Uri uri = data.getData();
            imagePath = getImagePath(mActivity, uri, null);
        }
        return imagePath;
    }

    //拍照和打开相册回调处理
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case Utils.MEDIA_TAKE_PHOTO:
//                if (resultCode == Activity.RESULT_OK) {
//                    try {
//                        Bitmap bmp = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri));
//                        finalPicture.setVisibility(View.VISIBLE);
//                        finalPicture.setImageBitmap(bmp);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//                break;
//            case Utils.MEDIA_CHOOSE_PICTURE:
//                String imagePath = Utils.handleImagePath(getActivity(), data);
//                if (!TextUtils.isEmpty(imagePath)) {
//                    Bitmap bmp = BitmapFactory.decodeFile(imagePath);
//                    finalPicture.setVisibility(View.VISIBLE);
//                    finalPicture.setImageBitmap(bmp);
//                } else {
//                    CommonToastUtils.showToast(getActivity(), "图片解析失败");
//                }
//                break;
//        }
//    }

    /**
     * 图片质量压缩方法
     *
     * @param bmp  要压缩的Bitmap
     * @param file 压缩图片存放的路径
     */
    public static void compressImage(Bitmap bmp, File file) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 20;
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        try {
            // 把压缩后的数据ByteArrayOutputStream存放到FileOutputStream中
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
            DLog.w("uploadimg", "压缩后图片大小:" + file.length() / 1024 + "K");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //判断字符长度是否合适,汉字为2个字符
    public static boolean isStrSuitable(String name, int suitLength) {
        if (TextUtils.isEmpty(name)) {
            return false;
        }
        boolean flag = true;
        int charLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        for (int i = 0; i < name.length(); i++) {
            String tmp = name.substring(i, i + 1);
            if (tmp.matches(chinese)) {
                charLength += 2;
            } else {
                charLength++;
            }
        }
        if (charLength > suitLength) {
            flag = false;
        }
        return flag;
    }

    private int getNameLength(String name) {
        int charLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        for (int i = 0; i < name.length(); i++) {
            String tmp = name.substring(i, i + 1);
            if (tmp.matches(chinese)) {
                charLength += 2;
            } else {
                charLength++;
            }
        }
        return charLength;
    }

    //获取当前版本名
    public static String getVersionName() {
        String versionName = "";
        Context context = BaseApplication.getAppContext();
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    //获取当前版本号
    public static int getVersionCode() {
        int versionName = 0;
        Context context = BaseApplication.getAppContext();
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            versionName = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    //查找特殊字符无脑写法
    public static boolean findSpecialChar(String str) {
        Matcher matcher = Pattern.compile("[`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）_——+|{}【】‘；：”“’。，、？]")
                .matcher(str);
        return matcher.find();
    }

    //正则：用户名，取值范围为a-z,A-Z,0-9,汉字
    public static boolean matchUserName(String str) {
        return matchRegex("^[a-zA-Z0-9\\u4e00-\\u9fa5]+$", str);
    }

    public static boolean matchRegex(String pattern, String str) {
        return Pattern.matches(pattern, str);
    }

    /**
     * 号码中间转换符号,区间为[begin,end)
     *
     * @param phoneNumber 需要转换的号码
     * @param begin       需要转换的开始位置
     * @param end         需要转换的结尾位置
     * @param pattern     需要拼接的符号,*或x
     * @return 转换后的字符串
     */
    private static String encodePhone(String phoneNumber, int begin, int end, String pattern) {
        StringBuilder sb = new StringBuilder();
        if (phoneNumber.length() > begin) {
            sb.append(phoneNumber.substring(0, begin));
            for (int i = 0; i < end - begin; i++) {
                sb.append(pattern);
            }
            if (phoneNumber.length() > end) {
                sb.append(phoneNumber.substring(end, phoneNumber.length()));
            }
        } else {
            for (int j = 0; j < phoneNumber.length(); j++) {
                sb.append(pattern);
            }
        }
        return sb.toString();
    }

    //判断是不是图片文件
    public static boolean isImageFile(String pathName) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        return options.outWidth != -1;
    }

    /**
     * 监听键盘弹出,使布局上移,scrollToView为需要展示的最下面的View,root一般为根布局
     */
    private void addKeyboardListener(final View root, final View scrollToView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //测量可见区域,并把宽高存入Rect
                root.getWindowVisibleDisplayFrame(rect);
                //不可见区域的高度,即键盘遮挡高度
                int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;
                if (rootInvisibleHeight > 100) {
                    int[] location = new int[2];
                    //获取View坐标,第一个为x值,第二个为Y值
                    scrollToView.getLocationInWindow(location);
                    //计算出指定View到可视区域底部的距离
                    int scrollHeight = location[1] + scrollToView.getHeight() - rect.bottom;
                    scrollHeight = scrollHeight < 0 ? 0 : scrollHeight;
                    root.scrollTo(0, scrollHeight);
                } else {
                    root.scrollTo(0, 0);
                }
            }
        });
    }

    // 判断是否有SIM卡
    public static boolean hasSimCard(Context context) {
        TelephonyManager telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int simState = 0;
        if (telMgr != null) {
            simState = telMgr.getSimState();
        }
        boolean result = true;
        if (simState == TelephonyManager.SIM_STATE_ABSENT || simState == TelephonyManager.SIM_STATE_UNKNOWN) {
            result = false;
        }
        return result;
    }

    public static boolean isNetWorkConnected(Context mContext) {
        if (mContext != null) {
            ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                if (networkInfo != null) {
                    return networkInfo.isAvailable();
                }
            }
        }
        return false;
    }

    //字节数组转化成十六进制字符串
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    //获取当前应用程序包名
    public static String getAppProcessName(Context context) {
        //当前应用pid
        int pid = android.os.Process.myPid();
        //任务管理类
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //遍历所有应用
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (info.pid == pid)//得到当前应用
                return info.processName;//返回包名
        }
        return "";
    }
}
