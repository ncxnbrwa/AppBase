package com.xiong.appbase.custom;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.internal.ProgressDrawable;
import com.scwang.smartrefresh.layout.internal.pathview.PathsView;
import com.scwang.smartrefresh.layout.util.DensityUtil;

/**
 * Created by iiMedia on 2018/4/18.
 * 通用下拉刷新头
 */

public class CommonRefreshHeader extends LinearLayout implements RefreshHeader {
    private TextView mHeaderText;//标题文本
    private PathsView mArrowView;//下拉箭头
    private ImageView mProgressView;//刷新动画视图
    private ProgressDrawable mProgressDrawable;//刷新动画

    public CommonRefreshHeader(Context context) {
        super(context);
        try {
            initView(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CommonRefreshHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CommonRefreshHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setGravity(Gravity.CENTER);
        mHeaderText = new TextView(context);
        mProgressDrawable = new ProgressDrawable();
        mArrowView = new PathsView(context);
        mProgressView = new ImageView(context);
        mProgressView.setImageDrawable(mProgressDrawable);
        mArrowView.parserColors(Color.parseColor("#ef5350"));
        mArrowView.parserPaths("M20,12l-1.41,-1.41L13,16.17V4h-2v12.17l-5.58,-5.59L4,12l8,8 8,-8z");
        addView(mProgressView, DensityUtil.dp2px(20), DensityUtil.dp2px(20));
        addView(mArrowView, DensityUtil.dp2px(20), DensityUtil.dp2px(20));
        addView(new View(context), DensityUtil.dp2px(20), DensityUtil.dp2px(20));
        addView(mHeaderText, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        setMinimumHeight(DensityUtil.dp2px(60));
    }

    @Override
    public void onPulling(float percent, int offset, int height, int extendHeight) {

    }

    @Override
    public void onReleased(RefreshLayout refreshLayout, int height, int extendHeight) {

    }

    @Override
    public void onReleasing(float percent, int offset, int headerHeight, int extendHeight) {
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;//指定为平移，不能null
    }

    @Override
    public void setPrimaryColors(int... colors) {
    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {
    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int height, int extendHeight) {
        //开始动画
        mProgressDrawable.start();
    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        mProgressDrawable.stop();
        if (success) {
            mHeaderText.setText("刷新成功");
        } else {
            mHeaderText.setText("刷新失败");
        }
        //延迟回弹时间
        return 300;
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState) {
            case None:
            case PullDownToRefresh:
                mHeaderText.setText("下拉开始刷新");
                mArrowView.setVisibility(VISIBLE);//显示下拉箭头
                mProgressView.setVisibility(GONE);//隐藏动画
                mArrowView.animate().rotation(0);//还原箭头方向
                break;
            case Refreshing:
                mHeaderText.setText("为您推荐中");
                mProgressView.setVisibility(VISIBLE);//显示加载动画
                mArrowView.setVisibility(GONE);//隐藏箭头
                break;
            case ReleaseToRefresh:
                mHeaderText.setText("释放立即刷新");
                mArrowView.animate().rotation(180);//显示箭头改为朝上
                break;
        }
    }
}
