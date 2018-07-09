package com.iimedia.appbase.view.webview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.view.View.OnKeyListener
import android.webkit.*
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.iimedia.appbase.view.webview.interfaces.JSInterface
import com.xiong.appbase.R
import com.xiong.appbase.utils.DLog


/**
 * Created by iiMedia on 2017/7/24.
 */
class KWebViewExt @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    val TAG = "WebView"
    internal var mWebView: KWebView? = null
    internal var mProgressBar: ProgressBar? = null
    internal var mRootView: LinearLayout? = null
    //    internal var mScrollView: NestedScrollView? = null
    internal var mNoImageMode: Boolean = false
    var titleListener: OnReceivedTitleListener? = null

    init {
        initView(context)
    }

    private fun initView(context: Context) {
        val view = View.inflate(context, R.layout.kweb_view_ext, this)
        mWebView = view.findViewById(R.id.web_view) as KWebView
        mProgressBar = view.findViewById(R.id.progress_bar) as ProgressBar
        mRootView = view.findViewById(R.id.kweb_root) as LinearLayout
//        mScrollView = view.findViewById(R.id.kweb_scroll) as NestedScrollView
    }

    fun loadUrl(url: String) {
        if (TextUtils.isEmpty(url)) return
        initWebview(url)
    }

    fun reload() {
        mWebView?.reload()
    }

    fun setNoImageMode(enable: Boolean) {
        mNoImageMode = enable
        mWebView?.clearCache(true)
    }

    fun setTextSize(size: WebSettings.TextSize) {
        mWebView?.settings?.textSize = size
    }

    fun setBackgroundTransparent() {
        mWebView?.setBackgroundColor(Color.TRANSPARENT)
        mWebView?.background?.alpha = 0
//        if (Preferences.getTheme() == 1) {
//            mWebView?.setBackgroundColor(Color.parseColor("#303030"))
//        } else {
//            mWebView?.setBackgroundColor(Color.parseColor("#ffffff"))
//        }
    }

    @SuppressLint("JavascriptInterface")
    fun addJavascriptInterface(listener: (Int, String) -> Unit) {
        mWebView?.addJavascriptInterface(JSInterface(listener), "android")
    }

    private fun initWebview(url: String) {

        mWebView?.loadUrl(url)
        DLog.e(TAG, "当前加载Url:" + url)

        // 懒加载图片
        mWebView?.settings?.blockNetworkImage = true

        // 设置WebViewClient
        mWebView?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                // 使用自己的WebView组件来响应Url加载事件，而不是使用默认浏览器器加载页面
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view?.loadUrl(request?.url.toString())
                } else {
                    view?.loadUrl(request.toString())
                }
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                mProgressBar?.visibility = View.VISIBLE
                mProgressBar?.progress = 15
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                mProgressBar?.visibility = View.GONE
                if (!mNoImageMode) view?.settings?.blockNetworkImage = false
                super.onPageFinished(view, url)
            }
        }

        // 设置WebChromeClient
        mWebView?.webChromeClient = object : WebChromeClient() {

            override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                val alertDialog = AlertDialog.Builder(context).setMessage(message)
                        .setPositiveButton("确定",
                                { _, _ -> result?.confirm() }).show()
                alertDialog.setCanceledOnTouchOutside(false)
                alertDialog.setOnKeyListener(
                        { _, keyCode, _ ->
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                result?.confirm()
                            }
                            false
                        })
                return true
            }

            override fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                val alertDialog = AlertDialog.Builder(context).setMessage(message)
                        .setPositiveButton("确定") { _, _ -> result?.confirm() }
                        .setNegativeButton("取消") { _, _ -> result?.cancel() }.show()
                alertDialog.setCanceledOnTouchOutside(false)
                alertDialog.setOnKeyListener { _, keyCode, _ ->
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        result?.cancel()
                    }
                    false
                }
                return true
            }

            override fun onJsPrompt(view: WebView?, url: String?, message: String?, defaultValue: String?, result: JsPromptResult?): Boolean {
                val editText = EditText(context)
                editText.setText(defaultValue)
                val alertDialog = AlertDialog.Builder(context).setTitle(message)
                        .setView(editText)
                        .setPositiveButton("确定",
                                { _, _ -> result?.confirm(editText.getText().toString()) })
                        .setNegativeButton("取消",
                                { _, _ -> result?.cancel() }).show()
                alertDialog.setCanceledOnTouchOutside(false)
                alertDialog.setOnKeyListener({ _, keyCode, _ ->
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        result?.cancel()
                    }
                    false
                })
                return true
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (newProgress > 15)
                    mProgressBar?.progress = newProgress
                super.onProgressChanged(view, newProgress)
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                titleListener?.receivedTitle(title)
            }

        }

        mWebView?.setOnKeyListener(OnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                // 表示按返回键
                if (keyCode == KeyEvent.KEYCODE_BACK && mWebView!!.canGoBack()) {
                    mWebView!!.goBack()
                    return@OnKeyListener true
                }
            }
            false
        })
    }

    interface OnReceivedTitleListener {
        fun receivedTitle(title: String?)
    }

    fun setOnReceivedTitleListener(listener: OnReceivedTitleListener) {
        this.titleListener = listener
    }

    fun goBack(): Boolean {
        if (mWebView!!.canGoBack()) {
            mWebView?.goBack()
            return true
        }
        return false
    }

    fun destroy() {
        mRootView?.removeAllViews()
        mWebView?.destroy()
        mWebView = null
    }
}