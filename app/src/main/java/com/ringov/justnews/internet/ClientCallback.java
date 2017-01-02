package com.ringov.justnews.internet;

import com.ringov.justnews.internet.parsing.SourceParser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Сергей on 29.12.2016.
 */
public interface ClientCallback<ParsingType> {
    void onResponseSuccess(JSONObject json, SourceParser<ParsingType> parser) throws JSONException;
    void onResponseFailure(Throwable t);
    void onParseFailure(JSONException e);
}
