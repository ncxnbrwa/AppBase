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

import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.xiong.appbase.R;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by iiMedia on 2018/4/24.
 * 金榜上拉加载footer
 */

public class JbREfreshFooter extends LinearLayout implements RefreshFooter {
    private TextView mFooterText;//标题文本
    private GifImageView gif;//刷新动画视图
    protected boolean mLoadmoreFinished = false;

    public JbREfreshFooter(Context context) {
        super(context);
        initView(context);
    }

    public JbREfreshFooter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public JbREfreshFooter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);
        mFooterText = new TextView(context);
        mFooterText.setTextSize(12f);
        mFooterText.setTextColor(Color.parseColor("#999999"));
        gif = new GifImageView(context);
        gif.setImageResource(R.drawable.crown);
        addView(gif, DensityUtil.dp2px(20), DensityUtil.dp2px(20));
        addView(mFooterText, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        setMinimumHeight(DensityUtil.dp2px(60));
    }

    @Override
    public boolean setNoMoreData(boolean noMoreData) {
        if (mLoadmoreFinished != noMoreData) {
            mLoadmoreFinished = noMoreData;
            if (noMoreData) {
                mFooterText.setText("我也是有底线的~");
                gif.setVisibility(GONE);
            } else {
                mFooterText.setText("上拉加载");
                gif.setVisibility(VISIBLE);
            }
        }
        return true;
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;//指定为平移，不能null
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int extendHeight) {

    }

    @Override
    public void onPulling(float percent, int offset, int height, int extendHeight) {

    }

    @Override
    public void onReleasing(float percent, int offset, int height, int extendHeight) {

    }

    @Override
    public void onReleased(RefreshLayout refreshLayout, int height, int extendHeight) {

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int extendHeight) {

    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        if (!mLoadmoreFinished) {
            if (success) {
                mFooterText.setText("加载成功");
            } else {
                mFooterText.setText("加载失败");
            }
            //延迟回弹时间
            return 300;
        }
        return 0;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        if (!mLoadmoreFinished) {
            switch (newState) {
                case None:
                case PullUpToLoad:
                    mFooterText.setText("上拉加载");
                    break;
                case Loading:
                    mFooterText.setText("正在加载");
                    break;
                case ReleaseToLoad:
                    mFooterText.setText("松开即可加载");
                    break;
            }
        }
    }
}
