package com.xiong.appbase.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.xiong.appbase.R;
import com.xiong.appbase.base.BaseApplication;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by iiMedia on 2018/5/9.
 * 倒计时Button
 */

public class TimeButton extends Button implements View.OnClickListener {

    private long length = 60 * 1000;// 倒计时长度
    //    private String textafter = "秒后重新发送";
    private String textbefore = "获取手机验证码";
    private final String TIME = "time";
    private final String CTIME = "ctime";
    private OnClickListener mOnclickListener;
    private Timer t;
    private TimerTask tt;
    //当前还剩时间
    private long time;
    //手机号满足标识,用于在倒计时结束时判断按钮是否可用
    private boolean isPhoneMatch = true;
    //是否正在计时
    private boolean isCounting;
    Map<String, Long> map = new HashMap<String, Long>();

    public TimeButton(Context context) {
        super(context);
        setOnClickListener(this);
    }

    public TimeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    @SuppressLint("HandlerLeak")
    Handler han = new Handler() {
        public void handleMessage(android.os.Message msg) {
            TimeButton.this.setText("重发(" + time / 1000 + ")");
            time -= 1000;
            if (time < 0) {
                if (isPhoneMatch) {
                    TimeButton.this.setEnabled(true);
                    TimeButton.this.setBackgroundResource(R.drawable.btn_enable_bg);
                } else {
                    TimeButton.this.setEnabled(false);
                    TimeButton.this.setBackgroundResource(R.drawable.btn_disabled_bg);
                }
                TimeButton.this.setText(textbefore);
                clearTimer();
            }
        }

        ;
    };

    private void initTimer() {
        time = length;
        t = new Timer();
//        isCounting = true;
//        tt = new TimerTask() {
//
//            @Override
//            public void run() {
//                Log.e("yung", time / 1000 + "");
//                han.sendEmptyMessage(0x01);
//            }
//        };
        startCount();
    }

    public void startCount() {
        isCounting = true;
        tt = new TimerTask() {

            @Override
            public void run() {
                han.sendEmptyMessage(0x01);
            }
        };
        this.setEnabled(false);
        this.setBackgroundResource(R.drawable.btn_disabled_bg);
        t.schedule(tt, 0, 1000);
    }

    private void clearTimer() {
        isCounting = false;
        if (tt != null) {
            tt.cancel();
            tt = null;
        }
        if (t != null)
            t.cancel();
        t = null;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        if (l instanceof TimeButton) {
            super.setOnClickListener(l);
        } else {
            this.mOnclickListener = l;
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnclickListener != null)
            mOnclickListener.onClick(v);
        initTimer();
//        this.setEnabled(false);
//        this.setBackgroundResource(R.drawable.btn_disabled_bg);
//        t.schedule(tt, 0, 1000);
    }

    /**
     * 和activity的onDestroy()方法同步
     */
    public void onDestroy() {
        if (BaseApplication.timeMap == null)
            BaseApplication.timeMap = new HashMap<String, Long>();
        BaseApplication.timeMap.put(TIME, time);
        BaseApplication.timeMap.put(CTIME, System.currentTimeMillis());
        clearTimer();
    }

    /**
     * 和activity的onCreate()方法同步
     */
    public void onCreate() {
        if (BaseApplication.timeMap == null)
            return;
        if (BaseApplication.timeMap.size() <= 0)// 这里表示没有上次未完成的计时
            return;
        long time = System.currentTimeMillis() - BaseApplication.timeMap.get(CTIME)
                - BaseApplication.timeMap.get(TIME);
        BaseApplication.timeMap.clear();
        if (time > 0)
            return;
        else {
            initTimer();
            this.time = Math.abs(time);
            t.schedule(tt, 0, 1000);
            this.setEnabled(false);
        }
    }

    /**
     * 设置到计时长度
     *
     * @param length 时间 默认毫秒
     * @return
     */
    public TimeButton setLength(long length) {
        this.length = length;
        return this;
    }

    public void setPhoneMatch(boolean phoneMatch) {
        isPhoneMatch = phoneMatch;
    }

    public boolean isCounting() {
        return isCounting;
    }
}
