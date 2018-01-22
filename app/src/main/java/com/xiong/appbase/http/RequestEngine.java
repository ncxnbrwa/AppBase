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

//    public static final String HOST = "http://yq.iimedia.cn/";
//    public static final String HOST = "http://10.1.1.93:8080/";
//    public static final String HOST = "http://10.1.1.186:8080/";
//    public static final String HOST = "http://testyq.iimedia.cn/";
    public static final String HOST = "https://eyq.iimedia.cn/";

    //登录
    public static final String LOGIN = "auto/login";

    //获取当前项目
    public static final String GET_CURRENT_PROJECT = "project/project_list";

    //添加项目
    public static final String ADD_PROJECT = "project/new_project";

    //根据关键字查询关联词
    public static final String QUERY_RELATED_WORD = "project/relatedword_by_keyword";

    //拉取主页文章
    public static final String GET_NEWS = "detail/getNews";

    //拉取对应项目预警
    public static final String GET_HISTOTY_WARNING = "early_w/get_history_warning";

    //拉取用户历史预警
    public static final String GET_USER_WARNING = "early_w/get_history_warning_byuser";

    //预警详情新闻
    public static final String GET_WARNING_DETAIL_NEWS = "early_w/get_datail_news_by_historyw";

    //获取用户信息
    public static final String GET_USER_INFO = "u/info";

    //热度指数
    public static final String HOT_HEAT = "real/hot_heat";

    //媒体占比
    public static final String MEDIA_DATA_RATIO = "real/source_data";

    //情感比例
    public static final String EMO_RATIO = "real/emotion_attributes";

    //情感走势
    public static final String EMO_TREND = "real/emotion_trend";

    private static OkHttpClient okClient = new OkHttpClient.Builder()
            .addInterceptor(getHttpLoggingInterceptor())
            .addInterceptor(new CookiesAddInterceptor())
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
