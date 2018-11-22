package com.xiong.appbase.custom.webviewInJava;

import android.webkit.JavascriptInterface;

public class JsActionObject {
    public static final int GOBACK = 0;
    private JsInterface iJsInterface;

    public JsActionObject(JsInterface iJsInterface) {
        this.iJsInterface = iJsInterface;
    }

    @JavascriptInterface
    void goBack() {
        iJsInterface.jsAction(GOBACK);
    }

}
