package com.iimedia.appbase.view.webview

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.webkit.WebSettings
import android.webkit.WebView

/**
 * Created by iiMedia on 2017/7/24.
 * 自定义WebView,预先定义好一些常规设置
 */

class KWebView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : WebView(context, attrs, defStyleAttr) {
    val TAG = "WebView"

    init {
        setWebSettings()
    }

    @SuppressLint("JavascriptInterface", "SetJavaScriptEnabled")
    private fun setWebSettings() {
        val settings = settings
        //设置webview自适应屏幕大小
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        //设置，可能的话使所有列的宽度不超过屏幕宽度
        //指定WebView的页面布局显示形式，调用该方法会引起页面重绘。默认值为LayoutAlgorithm#NARROW_COLUMNS。
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS

        //启动javascript交互
        settings.javaScriptEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        //缩放
        settings.setSupportZoom(true);
        //设置是否可缩放,会出现缩放工具
        settings.builtInZoomControls = true
        //隐藏缩放工具
        settings.displayZoomControls = false
        settings.allowContentAccess = true
        settings.allowFileAccess = true
        settings.databaseEnabled = true
        //缩放至屏幕的大小
        settings.loadWithOverviewMode = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        settings.pluginState = WebSettings.PluginState.ON
        settings.blockNetworkImage = true
        //设置缓存模式
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        //设置H5的缓存打开,默认关闭
        settings.setAppCacheEnabled(true)
        // 开启 DOM storage API 功能
        settings.domStorageEnabled = true
        settings.defaultTextEncodingName = "utf-8"
        //获取UserAgent
        var agentStr: String? = settings.userAgentString
        //更改UserAgent
//        settings.userAgentString = "$agentStr ngzbapp/${BuildConfig.VERSION_NAME}"
    }

}
