package com.xiong.appbase.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by iiMedia on 2018/5/16.
 */

public class UploadImgEngine {
    //图片上传
    public static final String BASE_URL_IMAGE_UPLOAD = "http://img.iimedia.cn/upload";
    public static final String BASE_URL_IMAGE = "http://img.iimedia.cn";
    public static final String BASE_URL_IMAGE2 = "http://img.iimedia.cn/";
    public static final String UPLOAD = "upload";
//    final String requestUrl = BASE_URL_IMAGE_UPLOAD + "?app_id=" + Constants.APP_ID;

    private static OkHttpClient okClient = new OkHttpClient.Builder()
            .addInterceptor(getHttpLoggingInterceptor())
            .connectTimeout(10, TimeUnit.SECONDS)
            .build();

    private static Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl(BASE_URL_IMAGE_UPLOAD)
            .baseUrl(BASE_URL_IMAGE2)
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
