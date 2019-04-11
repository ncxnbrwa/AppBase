package com.xiong.appbase.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class NoInterceptScrollView extends ScrollView {
    public NoInterceptScrollView(Context context) {
        super(context);
    }

    public NoInterceptScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoInterceptScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            onTouchEvent(ev);
            return false;
        }
        return true;
    }
}
