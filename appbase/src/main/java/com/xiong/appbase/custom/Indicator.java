package com.xiong.appbase.custom;


import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiong.appbase.R;
import com.xiong.appbase.utils.ScreenUtils;


//自定义进度框
public class Indicator extends DialogFragment {
    public static final String INDICATOR_TAG = "indicator_tag";
    private TextView tvMsg;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.translucence_dialog);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.progress_default, null);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        dialog.setContentView(view);
        tvMsg = view.findViewById(R.id.progress_message);
        //设置对话框大小
        RelativeLayout rootView = view.findViewById(R.id.progress_root_view);
        ViewGroup.LayoutParams lp = rootView.getLayoutParams();
        lp.width = (int) (ScreenUtils.getScreenWidth() * 0.75);
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        rootView.setLayoutParams(lp);
        return dialog;
    }

    public void configLoadingText(final String msg) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (tvMsg != null && !TextUtils.isEmpty(msg)) {
                    tvMsg.setText(msg);
                }
            }
        });
    }
}
