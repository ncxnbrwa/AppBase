package com.iimedia.appbase.view.webview.interfaces

import android.webkit.JavascriptInterface

/**
 * Created by iiMedia on 2017/9/12.
 */
open class JSInterface(val listener: (Int, String) -> Unit) {

    companion object {
        val GOTO_REGISTER_ACTIVITY = 0
        val GOTO_UGC_DETAIL_ACTIVITY = 1
        val GOTO_NEWS_DETAIL_ACTIVITY = 2
        val ACTION_COMMENT = 3
        val ACTION_LOVE_COMMENT = 4
        val SET_RELATE_LIST = 5
        val GOTO_EXPOSE = 6
    }

    @JavascriptInterface
    fun gotoRegister() {
        listener(GOTO_REGISTER_ACTIVITY, "")
    }

    @JavascriptInterface
    fun gotoUgcDetail() {
        listener(GOTO_UGC_DETAIL_ACTIVITY, "")
    }

    @JavascriptInterface
    fun xw_callNewsDetail(data: String) {
        listener(GOTO_NEWS_DETAIL_ACTIVITY, data)
    }

    @JavascriptInterface
    fun xw_commentOther(data: String) {
        listener(ACTION_COMMENT, data)
    }

    @JavascriptInterface
    fun xw_loveComment(data: String) {
        listener(ACTION_LOVE_COMMENT, data)
    }

    @JavascriptInterface
    fun xw_relatedNews(data: String) {
        listener(SET_RELATE_LIST, data)
    }

    @JavascriptInterface
    fun informComment(data: String) {
        listener(GOTO_EXPOSE, data)
    }
}