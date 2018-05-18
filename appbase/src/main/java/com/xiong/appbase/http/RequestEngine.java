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

    //轮播
    public static final String GET_BANNER = "posi/gpli";

    //最新榜单
    public static final String GET_LATEST_LIST = "api/rankInfo/newlist";

    //最热榜单
    public static final String GET_HOTTEST_LIST = "api/rankInfo/hotlist";

    //小众精选
    public static final String FEATURED_BRAND_LIST = "api/brand/minlist";

    //热门品牌
    public static final String HOT_BRAND_LIST = "api/brand/hotlist";

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

    //榜单大全一级分类
    public static final String CLASSIFY_ONE_LEVEL = "api/type/OneLevelType";

    //二级分类
    public static final String CLASSIFY_TWO_LEVEL = "api/type/getChildType";

    //二级分类获取榜单信息,三级分类
    public static final String CLASSIFY_THREE_LEVEL = "api/rankInfo/ranklist/{typeId}";

    //联系我们
    public static final String CONTACT_US = "api/contacts/";

    //发送验证码
    public static final String SEND_CODE = "tc/spm";

    //生成图形验证码
    public static final String GET_IMG_CODE = "tc/cvc";

    //图形验证码校验
    public static final String CHECK_IMG_CODE = "tc/pcvc";

    //注册
    public static final String REGISTER = "u/b/ur";

    //登录
    public static final String LOGIN = "u/b/upl";

    //第三方登录
    public static final String LOGIN_THIRD_PARTY = "u/b/ul";

    //忘记密码重置
    public static final String RESET_PASSWORD = "u/b/mpbp";

    //修改密码
    public static final String MODIFY_PASSWORD = "u/b/mpbpw";

    //修改个人信息
    public static final String UPDATE_USER_INFO = "u/b/mu";

    //添加/取消收藏
    public static final String COLLECT_ACTION = "u/i/uc";

    //获取我的收藏
    public static final String GET_COLLECT_DATA = "u/i/ugc";

    //初始化收藏
    public static final String INIT_COLLECT = "u/i/iugc";

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

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

    public static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

}
