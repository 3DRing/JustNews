package com.ringov.justnews.internet.retrofit.lenta;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Сергей on 02.01.2017.
 */

public interface LentaRetrofit {
    @GET("rss")
    Call<ResponseBody> getRss();
}
