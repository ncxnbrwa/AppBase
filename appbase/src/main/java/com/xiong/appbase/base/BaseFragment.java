package com.xiong.appbase.base;


import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.qmuiteam.qmui.util.QMUIDeviceHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.xiong.appbase.utils.DLog;
import com.xiong.appbase.utils.MyUtils;

import me.yokeyword.fragmentation.SupportFragment;


//public abstract class BaseFragment extends Fragment {
public abstract class BaseFragment extends SupportFragment {
    public BaseApplication mElfApp = null;
    private boolean isViewPrepared; // 标识fragment视图已经初始化完毕
    private boolean hasFetchData; // 标识已经触发过懒加载数据
    //    Unbinder unbinder;
    static Toast mToast;
    public Activity mActivity;
    protected View mStatusBarView;
    private ViewGroup rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mElfApp = BaseApplication.getInstance();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(getLayoutId(), container, false);
//        unbinder = ButterKnife.bind(this, view);
        init();
//        ViewGroup decorContentView = (ViewGroup) getBaseActivity().findViewById(android.R.id.content);
//        rootView = (ViewGroup) decorContentView.getChildAt(0);
//        rootView.removeView(view);
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewPrepared = true;
        canLoadData();
    }

    protected abstract int getLayoutId();

    protected void init() {
    }

    //该方法可以获取当前fragment是否可见
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            canLoadData();
        }
    }

    //懒加载
    private void canLoadData() {
        if (getUserVisibleHint() && isViewPrepared && !hasFetchData) {
            hasFetchData = true;
            if (getBaseActivity() != null) {
                lazyLoad();
            }
        }
    }

    //耗时操作全放这里
    protected void lazyLoad() {
    }


    public void showToast(String msg) {
        if (mToast != null)
            mToast.cancel();
        mToast = Toast.makeText(BaseApplication.getAppContext(), msg, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void showLoadingDialog() {
        if (getBaseActivity() != null) {
            getBaseActivity().showLoadingDialog();
        }
    }

    public void dismissLoadingDialog() {
        if (getBaseActivity() != null) {
            getBaseActivity().dismissLoadingDialog();
        }
    }

    //获取当前依赖的Activity
    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    @Override
    public void onPause() {
        DLog.d(getClass().getSimpleName(), "onPause");
        super.onPause();
    }

    @Override
    public void onResume() {
        DLog.d(getClass().getSimpleName(), "onResume");
        super.onResume();
    }

    protected void addStatusBar(@ColorRes int color) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        if (QMUIDeviceHelper.isMeizu() || QMUIDeviceHelper.isMIUI()) {
            doAddStatusBar(color);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            doAddStatusBar(color);
        }

    }

    protected void doAddStatusBar(@ColorRes int color) {
        //添加状态栏占位View,沉浸式状态栏主页多fragment情况需要
        if (mStatusBarView == null) {
            mStatusBarView = new View(getContext());
            int screenWidth = MyUtils.getScreenWidth();
            int statusBarHeight = QMUIStatusBarHelper.getStatusbarHeight(getBaseActivity());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(screenWidth, statusBarHeight);
            mStatusBarView.setLayoutParams(params);
            mStatusBarView.setBackgroundColor(ContextCompat.getColor(mActivity, color));
            mStatusBarView.requestLayout();
            if (rootView != null)
                rootView.addView(mStatusBarView, 0);
        }
    }

    protected void setStatusBarColor(@ColorRes int color) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        if (QMUIDeviceHelper.isMeizu() || QMUIDeviceHelper.isMIUI()) {
            doSetStatusBarColor(color);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            doSetStatusBarColor(color);
        }
    }

    protected void doSetStatusBarColor(@ColorRes int color) {
        if (mStatusBarView != null) {
            mStatusBarView.setBackgroundColor(ContextCompat.getColor(mActivity, color));
        }
    }

    @Override
    public void onDestroyView() {
        //销毁试图时把标志位都置为false
        isViewPrepared = false;
        hasFetchData = false;
        DLog.d(getClass().getSimpleName(), "onDestroyView");
//        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        DLog.d(getClass().getSimpleName(), "onDestroy");
        mActivity = null;
        super.onDestroy();
    }

    //放入传值的参数
    public void setInternalActivityParam(String key, Object object) {
        mElfApp.setInternalActivityParam(key, object);
    }

    //获取传值的参数
    public Object receiveInternalActivityParam(String key) {
        return mElfApp.receiveInternalActivityParam(key);
    }

}
