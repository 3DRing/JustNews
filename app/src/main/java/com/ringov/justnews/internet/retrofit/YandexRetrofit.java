package com.ringov.justnews.internet.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Сергей on 29.12.2016.
 */

public interface YandexRetrofit {
    @GET("index.rss")
    Call<ResponseBody> getTestRss();
}
