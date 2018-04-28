package com.xiong.appbase.custom;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.xiong.appbase.R;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by iiMedia on 2018/4/24.
 * 金榜下拉刷新头
 */

public class JbRefreshHeader extends LinearLayout implements RefreshHeader {
    private TextView mHeaderText;//标题文本
    private GifImageView gif;//刷新动画视图

    public JbRefreshHeader(Context context) {
        super(context);
        try {
            initView(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JbRefreshHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public JbRefreshHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);
        mHeaderText = new TextView(context);
        mHeaderText.setTextSize(10f);
        mHeaderText.setTextColor(Color.parseColor("#999999"));
        gif = new GifImageView(context);
        gif.setImageResource(R.drawable.crown);
        addView(gif, DensityUtil.dp2px(20), DensityUtil.dp2px(20));
        addView(mHeaderText, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        setMinimumHeight(DensityUtil.dp2px(60));
    }


    @Override
    public void onReleasing(float percent, int offset, int headerHeight, int extendHeight) {
    }

    @Override
    public void onReleased(RefreshLayout refreshLayout, int height, int extendHeight) {

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
    public void onPulling(float percent, int offset, int height, int extendHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {
    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int height, int extendHeight) {
    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
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
                mHeaderText.setText("下拉刷新");
                break;
            case Refreshing:
                mHeaderText.setText("正在刷新");
                break;
            case ReleaseToRefresh:
                mHeaderText.setText("松开即可刷新");
                break;
        }
    }
}
