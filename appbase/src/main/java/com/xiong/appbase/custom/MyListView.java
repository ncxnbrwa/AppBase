package com.xiong.appbase.custom;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class MyListView extends ListView {
    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int height = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
//        super.onMeasure(widthMeasureSpec, height);
//    }

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
