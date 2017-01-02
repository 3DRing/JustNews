package com.ringov.justnews.internet.parsing;

import com.ringov.justnews.model.ParseException;

import org.json.JSONObject;

/**
 * Created by Сергей on 02.01.2017.
 */

public interface SourceParser<ParsingType> {
    String fixResponseString(String response);
    ParsingType parse(JSONObject json) throws ParseException;
}
