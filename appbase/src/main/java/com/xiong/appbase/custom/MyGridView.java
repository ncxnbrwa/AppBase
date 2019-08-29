package com.xiong.appbase.custom;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;


/**
 * 防止和ScrollView滑动冲突的GridView
 */
public class MyGridView extends GridView {
    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float lastY;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                if (lastY > ev.getY()) {
                    // 如果是向上滑动，且不能滑动了，则让ScrollView处理
                    if (!canScrollList(1)) {
                        getParent().getParent().requestDisallowInterceptTouchEvent(false);
                        return false;
                    }
                } else if (lastY < ev.getY()) {
                    // 如果是向下滑动，且不能滑动了，则让ScrollView处理
                    if (!canScrollList(-1)) {
                        getParent().getParent().requestDisallowInterceptTouchEvent(false);
                        return false;
                    }
                }
                break;
        }
        lastY = ev.getY();
        return super.dispatchTouchEvent(ev);
    }
}
