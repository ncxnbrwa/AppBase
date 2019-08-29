package com.xiong.appbase.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xiong on 2017/11/9.
 * Retrofit请求
 */

public class RequestEngine {

    //    public static final String HOST = "http://10.1.1.186:11019/";
    public static final String HOST = "http://testrankingapi.imchance.com/";
    public static final String HOST2 = "http://www.iimedia.cn/";

    //最新榜单
    public static final String GET_LATEST_LIST = "api/rankInfo/newlist";

    //榜单属性
    public static final String RANKING_PROPERTY = "api/propertyValue/list/property/{property}";

    //榜单品牌详情
    public static final String RANKING_BRAND_DETAIL = "api/brand/list";

    //榜单详情
    public static final String RANKING_DETAIL = "api/rankInfo/{rankInfoId}";

    //入选榜单
    public static final String RANKING_SELECT_LIST = "api/rankInfo/brand/{brandId}";

    //获取品牌对应榜单排名
    public static final String RANKING_INDEX = "api/brand/getBrandRank";

    //品牌详情
    public static final String BRAND_DETAIL = "api/brand/{brandId}";

    //二级分类获取榜单信息,三级分类
    public static final String CLASSIFY_THREE_LEVEL = "api/rankInfo/ranklist/{typeId}";

    private static OkHttpClient okClient = new OkHttpClient.Builder()
            .addInterceptor(getHttpLoggingInterceptor())
//            .addInterceptor(new CookiesAddInterceptor())
//            .addInterceptor(new CookiesReceiverInterceptor())
            .connectTimeout(10, TimeUnit.SECONDS)
            .build();

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okClient)
            .build();

    private static Retrofit retrofit2 = new Retrofit.Builder()
            .baseUrl(HOST2)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okClient)
            .build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

    public static <S> S createService2(Class<S> serviceClass) {
        return retrofit2.create(serviceClass);
    }

    public static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

}
