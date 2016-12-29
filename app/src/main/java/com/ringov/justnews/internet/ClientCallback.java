package com.ringov.justnews.internet;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Сергей on 29.12.2016.
 */
public interface ClientCallback {
    void onResponseSuccess(JSONObject json) throws JSONException;
    void onResponseFailure(Throwable t);
    void onParseFailure(JSONException e);
}
