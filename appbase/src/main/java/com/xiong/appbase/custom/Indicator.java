package com.xiong.appbase.custom;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;

import com.xiong.appbase.R;
import com.xiong.appbase.utils.MyUtils;


//自定义进度框
public class Indicator extends Dialog {
    private LayoutInflater mInflater;
    private View mLayoutView;
    private int mlayout;

    public Indicator(Context context) {
        super(context, R.style.indicator_dialog);
        mlayout = R.layout.progress_default;
    }

    public Indicator(Context context, int layout) {
        super(context, R.style.indicator_dialog);
        mlayout = layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInflater = this.getLayoutInflater();
        mLayoutView = mInflater.inflate(mlayout, null);
        setContentView(mLayoutView);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        setDialogSize();
    }

    //设置对话框大小
    private void setDialogSize() {
        LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = (int) (MyUtils.getScreenWidth() * 0.75);
        layoutParams.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.dimAmount = 0f;
        getWindow().setAttributes(layoutParams);
    }

}
