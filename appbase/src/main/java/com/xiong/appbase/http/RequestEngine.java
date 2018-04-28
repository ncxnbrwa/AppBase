package com.xiong.appbase.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by iiMedia on 2017/11/9.
 * Retrofit请求
 */

public class RequestEngine {

    public static final String HOST = "http://10.1.1.186:11019/";
    public static final String HOST2 = "http://10.1.1.186:8085/";

    //示例
    public static final String GET_BANNER = "posi/gpli";


    private static OkHttpClient okClient = new OkHttpClient.Builder()
            .addInterceptor(getHttpLoggingInterceptor())
//            .addInterceptor(new CookiesAddInterceptor())
//            .addInterceptor(new CookiesReceiverInterceptor())
            .connectTimeout(6, TimeUnit.SECONDS)
            .build();

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okClient)
            .build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

    public static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

}
