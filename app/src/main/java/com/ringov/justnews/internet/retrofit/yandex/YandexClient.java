package com.ringov.justnews.internet.retrofit.yandex;

import com.ringov.justnews.SrcStr;
import com.ringov.justnews.internet.parsing.UnifiedParser;
import com.ringov.justnews.internet.retrofit.RSSClient;
import com.ringov.justnews.model.News;
import com.ringov.justnews.model.ParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.*;

/**
 * Created by Сергей on 29.12.2016.
 */

public class YandexClient extends RSSClient<YandexRetrofit, List<News>> {

    public YandexClient(){
        retrofit =  new retrofit2.Retrofit.Builder()
                .baseUrl(SrcStr.YANDEX_URL)
                .build()
                .create(YandexRetrofit.class);
    }

    @Override
    public Call<ResponseBody> getRSS() {
        return retrofit.getRss();
    }

    @Override
    public String fixResponseString(String response) {
        String clearResult = UnifiedParser.fixString(response);
        return clearResult;
    }

    @Override
    public List<News> parse(JSONObject json) throws ParseException {
        return UnifiedParser.parseListOfNews(json, "dd MMM yyyy HH:mm:ss Z");
    }
}
