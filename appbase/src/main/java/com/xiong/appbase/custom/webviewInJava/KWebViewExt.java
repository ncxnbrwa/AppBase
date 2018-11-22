package com.xiong.appbase.custom.webviewInJava;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.xiong.appbase.R;


public class KWebViewExt extends LinearLayout {
    private static final String TAG = "KWebView";
    KWebView mWebView;
    ProgressBar mProgressBar;
    LinearLayout mRootView;

    public KWebViewExt(Context context) {
        super(context);
        initView(context);
    }

    public KWebViewExt(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public KWebViewExt(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.kweb_view_ext, this);
        mWebView = view.findViewById(R.id.web_view);
        mProgressBar = view.findViewById(R.id.progress_bar);
        mRootView = view.findViewById(R.id.kweb_root);
        initWebView();
    }

    private void initWebView() {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                } else {
                    view.loadUrl(request.toString());
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setProgress(15);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mProgressBar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress > 15) {
                    mProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

        });
        mWebView.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                        mWebView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public void loadUrl(String url) {
        if (TextUtils.isEmpty(url)) return;
        mWebView.loadUrl(url);
    }

    public void reload() {
        if (mWebView == null) return;
        mWebView.reload();
    }

    @SuppressLint("AddJavascriptInterface")
    public void addJavascriptInterface(JsInterface jsInterface) {
        mWebView.addJavascriptInterface(new JsActionObject(jsInterface), "Android");
    }

    public boolean goback() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return false;
    }

    public void destroy() {
        mRootView.removeAllViews();
        mWebView.destroy();
        mWebView = null;
    }
}
