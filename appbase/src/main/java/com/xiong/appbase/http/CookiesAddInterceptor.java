package com.xiong.appbase.http;

import com.xiong.appbase.utils.DLog;
import com.xiong.appbase.utils.ELS;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by iiMedia on 2017/12/12.
 * 添加Cookies的拦截器
 */

public class CookiesAddInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        HashSet<String> cookieSet = (HashSet<String>) ELS.getInstance().getCookieSet();
        for (String cookie : cookieSet) {
            builder.addHeader("Cookie", cookie);
            DLog.w("OkHttp", "添加Cookie:" + cookie);
        }
        //必加,后台框架处理需要
        builder.addHeader("x-requested-with", "XMLHttpRequest");
        return chain.proceed(builder.build());
    }
}
