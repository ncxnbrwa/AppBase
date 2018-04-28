package com.xiong.appbase.base;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xiong.appbase.utils.DLog;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment {
    public BaseApplication mElfApp = null;
    private boolean isViewPrepared; // 标识fragment视图已经初始化完毕
    private boolean hasFetchData; // 标识已经触发过懒加载数据
    Unbinder unbinder;
    static Toast mToast;
    public Activity mActivity;

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
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        ViewGroup decorContentView = (ViewGroup) getBaseActivity().findViewById(android.R.id.content);
        ViewGroup rootView = (ViewGroup) decorContentView.getChildAt(0);
        rootView.removeView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewPrepared = true;
        canLoadData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    protected abstract int getLayoutId();

    protected abstract void init();

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
    public void onDestroyView() {
        //销毁试图时把标志位都置为false
        isViewPrepared = false;
        hasFetchData = false;
        DLog.d(getClass().getSimpleName(), "onDestroyView");
        super.onDestroyView();
        unbinder.unbind();
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
