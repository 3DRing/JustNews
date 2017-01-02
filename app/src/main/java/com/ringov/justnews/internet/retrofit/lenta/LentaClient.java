package com.ringov.justnews.internet.retrofit.lenta;

import com.ringov.justnews.SrcStr;
import com.ringov.justnews.internet.parsing.UnifiedParser;
import com.ringov.justnews.internet.retrofit.RSSClient;
import com.ringov.justnews.model.News;
import com.ringov.justnews.model.ParseException;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Сергей on 02.01.2017.
 */

public class LentaClient extends RSSClient<LentaRetrofit, List<News>> {

    public LentaClient(){
        retrofit =  new retrofit2.Retrofit.Builder()
                .baseUrl(SrcStr.LENTA_URL)
                .build()
                .create(LentaRetrofit.class);
    }

    @Override
    public Call<ResponseBody> getRSS() {
        return retrofit.getRss();
    }

    @Override
    public String fixResponseString(String response) {
        String clearResult = UnifiedParser.fixString(response)
                .replace("<![CDATA[", "")
                .replace("]]>", "")
                .replace("<br />","");      // TODO rewrite with regexs
        return clearResult;
    }

    @Override
    public List<News> parse(JSONObject json) throws ParseException {
        return UnifiedParser.parseListOfNews(json, "EEE, dd MMM yyyy HH:mm:ss Z");
    }
}
