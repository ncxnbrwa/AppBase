package com.xiong.appbase.http;

import com.xiong.appbase.Base.BaseApplication;
import com.xiong.appbase.Base.utils.DLog;
import com.xiong.appbase.Base.utils.ELS;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by iiMedia on 2017/12/12.
 * 获取Cookies的拦截器
 */

public class CookiesReceiverInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        ELS els = ELS.getInstance(BaseApplication.getAppContext());
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookieSet = new HashSet<>();
            for (String cookie : originalResponse.headers("Set-Cookie")) {
                cookieSet.add(cookie);
            }
            els.saveCookieSet(cookieSet);
        }
        DLog.w("Request", "得到的Cookie:" + els.getCookieSet().toString());
        return originalResponse;
    }
}
