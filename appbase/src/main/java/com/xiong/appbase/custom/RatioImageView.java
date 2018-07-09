package com.xiong.appbase.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.xiong.appbase.R;

/**
 * Created by iiMedia on 2018/6/22.
 */

public class RatioImageView extends ImageView {
    float ratio;

    public RatioImageView(Context context) {
        super(context);
    }

    public RatioImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView);
        ratio = ta.getFloat(R.styleable.RatioImageView_ratio, 1.0f);
        ta.recycle();
    }

    public RatioImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = (int) (width * ratio);
        setMeasuredDimension(width, height);
    }
}
