package com.ringov.justnews.internet.retrofit;

import com.ringov.justnews.internet.parsing.SourceParser;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Сергей on 29.12.2016.
 */

public abstract class RSSClient<ClientType, ParsingType> implements SourceParser<ParsingType> {

    protected ClientType retrofit;

    public abstract Call<ResponseBody> getRSS();
}
