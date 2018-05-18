package com.xiong.appbase.http;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by iiMedia on 2018/5/16.
 */

public interface UploadImgService {

    @Multipart
    @POST(UploadImgEngine.UPLOAD)
    Call<ResponseBody> upload(@Part MultipartBody.Part file);

//    @Multipart
//    @POST(UploadImgEngine.UPLOAD)
//    Call<ResponseBody> upload(@Part("avatar.jpg") RequestBody file);
}
