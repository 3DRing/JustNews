package com.ringov.justnews.internet.retrofit;

import com.ringov.justnews.SrcStr;

import okhttp3.ResponseBody;
import retrofit2.*;

/**
 * Created by Сергей on 29.12.2016.
 */

public class YandexClient extends RSSClient<YandexRetrofit>{

    public YandexClient(){
        retrofit =  new retrofit2.Retrofit.Builder()
                .baseUrl(SrcStr.YANDEX_URL)
                .build()
                .create(YandexRetrofit.class);
    }

    @Override
    public Call<ResponseBody> getRSS() {
        return retrofit.getTestRss();
    }
}
