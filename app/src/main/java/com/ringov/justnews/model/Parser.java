package com.ringov.justnews.model;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Сергей on 29.12.2016.
 */

public interface Parser<DataType, Source> {
    List<DataType> parse(JSONObject json, Source source) throws NewsParseException;
}
