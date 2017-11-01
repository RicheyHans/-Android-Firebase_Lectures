package com.example.mcbud.firebase_basic2_171031;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by mcbud on 2017-11-01.
 */

public interface IRetro {
    // 리턴타입 함수명(인자)
    @POST("sendNotification")
    Call<ResponseBody> sendNotification(@Body RequestBody postdata);       // http 바디 형태로 데이터가 들어온다
}
