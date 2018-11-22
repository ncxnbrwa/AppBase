package com.xiong.appbase.custom.webviewInJava;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class KWebView extends WebView {

    public KWebView(Context context) {
        super(context);
        setWebSettings();
    }

    public KWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWebSettings();
    }

    public KWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWebSettings();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setWebSettings(){
        WebSettings settings = getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        //启动javascript交互
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        //隐藏缩放工具
        settings.setDisplayZoomControls(false);
        settings.setAllowContentAccess(true);
        settings.setAllowFileAccess(true);
        settings.setDatabaseEnabled(true);
        //缩放至屏幕的大小
        settings.setLoadWithOverviewMode(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");
    }

}
