package com.xiong.appbase.http;

/**
 * Created by ncx on 2019/12/25
 * API写法参考类
 */
public class APIConstants {
    //统一都带上imei,暂时还没用

//    @POST(RequestEngine.GET_BANNER)
//    Call<BannerBean> getBanner(@Query("position") int position,
//                               @Query("imei") String imei);
//
//    //page为页码,默认0,size为每页数量,默认10
//    @GET(RequestEngine.GET_LATEST_LIST)
//    Call<RankingListBean> getLatestList(@Query("page") int page,
//                                        @Query("size") int size,
//                                        @Query("imei") String imei);
//
//    //同上
//    @GET(RequestEngine.GET_HOTTEST_LIST)
//    Call<RankingListBean> getHottestList(@Query("page") int page,
//                                         @Query("size") int size,
//                                         @Query("imei") String imei);
//
//    //小众精选
//    @GET(RequestEngine.FEATURED_BRAND_LIST)
//    Call<BrandListBean> getFeatruedList(@Query("imei") String imei);
//
//    //热门品牌
//    @GET(RequestEngine.HOT_BRAND_LIST)
//    Call<BrandListBean> getHotBrandList(@Query("imei") String imei);
//
//    //property 替换url中的 {property},比如综合为35
//    //rankInfoId 榜单id
//    @GET(RequestEngine.RANKING_PROPERTY)
//    Call<RankingPropertyBean> getRankingProPerty(@Path("property") int property,
//                                                 @Query("rankInfoId") int rankInfoId,
//                                                 @Query("imei") String imei);
//
//    //榜单品牌详情
//    @GET(RequestEngine.RANKING_BRAND_DETAIL)
//    Call<RankingBrandListBean> getRankingBrandDetail(@Query("rankInfoId") int rankInfoId,
//                                                     @Query("imei") String imei);
//
//    //榜单详情
//    @GET(RequestEngine.RANKING_DETAIL)
//    Call<RankingDetailBean> getRankingDetail(@Path("rankInfoId") int rankInfoId,
//                                             @Query("imei") String imei);
//
//    //品牌详情
//    @GET(RequestEngine.BRAND_DETAIL)
//    Call<BrandDetailBean> getBrandDetail(@Path("brandId") int brandId,
//                                         @Query("imei") String imei);
//
//    //入选榜单
//    @GET(RequestEngine.RANKING_SELECT_LIST)
//    Call<RankingSelectBean> getBrandSelect(@Path("brandId") int brandId,
//                                           @Query("page") int page,
//                                           @Query("size") int size,
//                                           @Query("imei") String imei);
//
//    //根据品牌获取榜单排名
//    //id,品牌ID
//    //rankid ,榜单ID
//    @POST(RequestEngine.RANKING_INDEX)
//    Call<RankingIndexBean> getRankingIndex(@Query("id") int brandId,
//                                           @Query("rankId") int rankId,
//                                           @Query("imei") String imei);
//
//    //榜单大全一级分类
//    @GET(RequestEngine.CLASSIFY_ONE_LEVEL)
//    Call<ClassifyLevelBean> getOneLevel(@Query("imei") String imei);
//
//    //榜单大全二级分类
//    @GET(RequestEngine.CLASSIFY_TWO_LEVEL)
//    Call<ClassifyLevelBean> getTwoLevel(@Query("id") int id,
//                                        @Query("imei") String imei);
//
//    @GET(RequestEngine.CLASSIFY_THREE_LEVEL)
//    Call<RankingListBean> getThreeLevel(@Path("typeId") int typeId,
//                                        @Query("imei") String imei);
//
//    @POST(RequestEngine.CONTACT_US)
//    Call<ContactUsBean> contactUs(@Query("phone") String phone,
//                                  @Query("email") String email,
//                                  @Query("name") String name,
//                                  @Query("message") String message,
//                                  @Query("imei") String imei);
//
//    //获取图形验证码
//    @GET(RequestEngine.GET_IMG_CODE)
//    Call<ResponseBody> getImgCode(@Query("ph") String ph,
//                                  @Query("imei") String imei);
//
//    //校验图形验证码
//    @POST(RequestEngine.CHECK_IMG_CODE)
//    Call<CommonResultBean> checkImgCode(@Query("ph") String ph,
//                                        @Query("vc") String vc);
//
//    //发送验证码ph：手机号码；pus：1注册；2：其他；vc:图形验证码;imei：手机标识；
//    @POST(RequestEngine.SEND_CODE)
//    Call<CommonCodeBean> sendCode(@Query("ph") String ph,
//                                  @Query("pus") int pus,
//                                  @Query("vc") String vc,
//                                  @Query("imei") String imei);
//
//    @POST(RequestEngine.REGISTER)
//    Call<RegisterBean> register(@QueryMap HashMap<String, Object> reqData);
//
//    //参数：phone：手机号码；pw：密码
//    @POST(RequestEngine.LOGIN)
//    Call<LoginBean> login(@Query("phone") String phone,
//                          @Query("pw") String pw,
//                          @Query("imei") String imei);
//
//    @POST(RequestEngine.LOGIN_THIRD_PARTY)
//    Call<LoginBean> loginThirdParty(@QueryMap HashMap<String, Object> map);
//
//    //imei：手机标识；ph：手机号；pw：新密码；phoneCode：手机验证码
//    @POST(RequestEngine.RESET_PASSWORD)
//    Call<CommonCodeBean> resetPassword(@Query("ph") String ph,
//                                       @Query("pw") String pw,
//                                       @Query("phoneCode") String phoneCode,
//                                       @Query("imei") String imei);
//
//    //imei：手机标识；oldPass:原密码；newPass:新密码；userId：用户Id
//    @POST(RequestEngine.MODIFY_PASSWORD)
//    Call<CommonCodeBean> modifyPassword(@Query("oldPass") String oldPass,
//                                        @Query("newPass") String newPass,
//                                        @Query("userId") String userId,
//                                        @Query("imei") String imei);
//
//    @POST(RequestEngine.UPDATE_USER_INFO)
//    Call<CommonUserBean> updateUserInfo(@QueryMap HashMap<String, Object> map);
//
//    // type：0：品牌；1：榜单；valId：品牌/榜单对应标识（id）;
//    // userId:用户标识；dotype：0：新增，1：移除
//    @POST(RequestEngine.COLLECT_ACTION)
//    Call<CommonCodeBean> collectAction(@Query("type") int type,
//                                       @Query("valId") int valId,
//                                       @Query("userId") String userId,
//                                       @Query("dotype") int dotype,
//                                       @Query("imei") String imei);
//
//    //type：0：品牌；1：榜单；userId:用户标识；pageNum:页码；size:每页条数
//    @POST(RequestEngine.GET_COLLECT_DATA)
//    Call<CollectRankingBean> getCollectRankingData(@Query("type") int type,
//                                                   @Query("userId") String userId,
//                                                   @Query("pageNum") int pageNum,
//                                                   @Query("size") int size,
//                                                   @Query("imei") String imei);
//
//    @POST(RequestEngine.GET_COLLECT_DATA)
//    Call<CollectBrandBean> getCollectBrandData(@Query("type") int type,
//                                               @Query("userId") String userId,
//                                               @Query("pageNum") int pageNum,
//                                               @Query("size") int size,
//                                               @Query("imei") String imei);
//
//    //type：0：品牌；1：榜单；2：全部；userId：用户标识；imei:用户手机
//    @POST(RequestEngine.INIT_COLLECT)
//    Call<InitCollectBean> initCollectData(@Query("type") int type,
//                                          @Query("userId") String userId,
//                                          @Query("imei") String imei);
//
//    //key:搜索关键字；value:用户id；type：搜索接口来源：0：app;1:h5;2:pc;pageNum:页码；size:每页条数
//    @POST(RequestEngine.SEARCH)
//    Call<SearchBean> querySearch(@Query("key") String key,
//                                 @Query("value") String value,
//                                 @Query("type") int type,
//                                 @Query("pageNum") int pageNum,
//                                 @Query("size") int size,
//                                 @Query("imei") String imei);
//
//    //imei:手机标识；userId：用户标识
//    @POST(RequestEngine.TOPIC_LIST)
//    Call<TopicBean> getTopicList(@Query("imei") String imei,
//                                 @Query("userId") String userId);
//
//    //imei:手机标识；userId:用户标识。sId：专题唯一标识
//    @POST(RequestEngine.TOPIC_DETAIL)
//    Call<TopicDetailBean> getTopicDetail(@Query("imei") String imei,
//                                         @Query("userId") String userId,
//                                         @Query("sId") int sId);
//
//    //type:880(固定)page_num:页码数；page_count:每页条数；url:固定不变
//    @POST(RequestEngine.INFO_LIST)
//    Call<ArrayList<InfoListData>> getInfoList(@Query("type") int type,
//                                              @Query("page_num") int pageNum,
//                                              @Query("page_count") int pageCount,
//                                              @Query("url") String url);
//
//    //userId:用户id;imei:手机标识；brandId:品牌id
//    @POST(RequestEngine.BRAND_DG)
//    Call<BrandDetailDgBean> getBrandDg(@Query("userId") String userId,
//                                       @Query("imei") String imei,
//                                       @Query("brandId") int brandId);
}
