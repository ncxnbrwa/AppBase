package com.xiong.appbase.base;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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

import me.yokeyword.fragmentation.SupportActivity;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;


//基础activity
//public abstract class BaseActivity extends SwipeBackActivity {
//public abstract class BaseActivity extends AppCompatActivity {
public abstract class BaseActivity extends SupportActivity {
    public BaseApplication mApp = null;
    //    Unbinder unbinder;
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
//        unbinder = ButterKnife.bind(this);

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
    public void toActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        this.startActivity(intent);
    }

    public void showToast(String msg) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(BaseApplication.getAppContext(), msg, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void showLoadingDialog() {
        if (isFinishing()) return;
        if (mProgressDialog == null) {
            mProgressDialog = new Indicator(this);
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    public void dismissLoadingDialog() {
        if (isFinishing()) return;
        if (mProgressDialog != null) {
            if (mProgressDialog.isShowing()) {
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
//        unbinder.unbind();
        super.onDestroy();
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
