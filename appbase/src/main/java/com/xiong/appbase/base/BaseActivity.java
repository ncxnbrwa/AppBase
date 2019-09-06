package com.xiong.appbase.base;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.umeng.analytics.MobclickAgent;
import com.xiong.appbase.R;
import com.xiong.appbase.custom.Indicator;
import com.xiong.appbase.http.UploadImgEngine;
import com.xiong.appbase.http.UploadImgService;
import com.xiong.appbase.utils.DLog;
import com.xiong.appbase.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportActivity;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;


//基础activity
//public abstract class BaseActivity extends SwipeBackActivity {
//public abstract class BaseActivity extends AppCompatActivity {
public abstract class BaseActivity extends SupportActivity {
    public BaseApplication mApp = null;
    static Toast mToast;
    //    QMUITipDialog loadingDialog;
    Indicator mProgressDialog;
//    QMUITipDialogWrapper loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ScreenUtils.adaptScreen4VerticalSlide(this, 375);
        //输出Debug信息
        DLog.d(getClass().getSimpleName(), "onCreate");
        mApp = BaseApplication.getInstance();
        //加activity
        mApp.pushActivity(this);

//        if (loadingDialog == null)
//            loadingDialog = ComponentsUtils.getLoadingDialog(this, "加载中...");
//        setInitialConfiguration();
        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarLightMode(this);
    }

    protected abstract int getLayoutId();

    @Override
    protected void onResume() {
        DLog.d(getClass().getSimpleName(), "onResume");
        super.onResume();
        MobclickAgent.onResume(this);
    }

    /**
     * 获取当前Activity
     */
    public BaseActivity getBaseActivity() {
        return this;
    }

    //页面跳转的方法
    protected void toActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        this.startActivity(intent);
    }

    protected void showToast(String msg) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(BaseApplication.getAppContext(), msg, Toast.LENGTH_SHORT);
        mToast.show();
    }

    protected void showLoadingDialog() {
        if (isFinishing()) return;
        if (mProgressDialog == null) {
            mProgressDialog = new Indicator();
        }
        if (!mProgressDialog.isVisible()) {
            mProgressDialog.show(getSupportFragmentManager(), Indicator.INDICATOR_TAG);
        }
    }

    protected void dismissLoadingDialog() {
        if (isFinishing()) return;
        if (mProgressDialog != null) {
            if (mProgressDialog.isVisible()) {
                mProgressDialog.dismiss();
            }
        }
    }

    @Override
    protected void onPause() {
        DLog.d(getClass().getSimpleName(), "onPause");
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        DLog.d(getClass().getSimpleName(), "onStop");
    }

    @Override
    protected void onDestroy() {
        DLog.d(getClass().getSimpleName(), "onDestroy");
//        mActivity = null;
        //activity销毁时弹出栈
        mApp.popActivity(this);
        DLog.d(".mActivityStack.size", "" + mApp.mActivityStack.size());
        super.onDestroy();
    }

    //**********************************权限申请封装**********************************//
    private int REQUEST_CODE_PERMISSION = 0x00099;

    //请求权限,外部调用
    protected void requestPermission(String[] permissions, int requestCode) {
        this.REQUEST_CODE_PERMISSION = requestCode;
        if (checkPermissions(permissions)) {
            permissionSuccess(REQUEST_CODE_PERMISSION);
        } else {
            List<String> needPermissions = getDeniedPermissions(permissions);
            ActivityCompat.requestPermissions(this, needPermissions.toArray(new String[needPermissions.size()]), REQUEST_CODE_PERMISSION);
        }
    }

    //检测所有的权限是否都已授权
    private boolean checkPermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    //获取权限集中需要申请权限的列表
    private List<String> getDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                needRequestPermissionList.add(permission);
            }
        }
        return needRequestPermissionList;
    }


    //系统请求权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (verifyPermissions(grantResults)) {
                permissionSuccess(REQUEST_CODE_PERMISSION);
            } else {
                permissionFail(REQUEST_CODE_PERMISSION);
                showTipsDialog();
            }
        }
    }

    //确认所有的权限是否都已授权
    private boolean verifyPermissions(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    //显示权限提示对话框
    private void showTipsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示信息")
                .setMessage("当前应用缺少必要权限，该功能暂时无法使用。如若需要，请单击【确定】按钮前往设置中心进行权限授权。")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                }).show();
    }

    //启动当前应用设置页面
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    //获取权限成功,子类回调
    public void permissionSuccess(int requestCode) {
        DLog.d(getClass().getSimpleName(), "获取权限成功=" + requestCode);
    }

    //权限获取失败,子类回调
    public void permissionFail(int requestCode) {
        DLog.d(getClass().getSimpleName(), "获取权限失败=" + requestCode);
    }

    //放入传值的参数
    public void setInternalActivityParam(String key, Object object) {
        mApp.setInternalActivityParam(key, object);
    }

    //获取传值的参数
    public Object receiveInternalActivityParam(String key) {
        return mApp.receiveInternalActivityParam(key);
    }

    //实现透明状态栏
    public void setInitialConfiguration() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT == 19) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            ViewGroup decorContentView = findViewById(android.R.id.content);
            ViewGroup rootView = (ViewGroup) decorContentView.getChildAt(0);
            if (rootView != null) {
                rootView.setFitsSystemWindows(true);
                rootView.setClipToPadding(true);
            }
        } else if (Build.VERSION.SDK_INT >= 21) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION |
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View
                    .SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.transparent));

            ViewGroup decorContentView = findViewById(android.R.id.content);
            ViewGroup rootView = (ViewGroup) decorContentView.getChildAt(0);
            if (rootView != null) {
                rootView.setFitsSystemWindows(true);
                rootView.setClipToPadding(true);
            }
        }
    }

}
