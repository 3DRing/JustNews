package com.ringov.justnews.model;

import com.ringov.justnews.internet.SOURCE;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Сергей on 29.12.2016.
 */

public class NewsParser implements Parser<News, SOURCE> {
    @Override
    public List<News> parse(JSONObject json, SOURCE source) throws NewsParseException {
        List<News> result = new LinkedList<>();
        switch (source) {
            case YANDEX:
                JSONArray array = null;
                try {
                    array = json.getJSONObject("rss")
                            .getJSONObject("channel").getJSONArray("item");

                    if (array == null) {
                        throw new NewsParseException("unexpected behaviour while getting news JSON array");
                    }
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject crtJSON = array.getJSONObject(i);
                        String title = crtJSON.getString("title").replace("&quot;","\"");
                        String link = crtJSON.getString("link");
                        String text = crtJSON.getString("description").replace("&quot;","\"");
                        String dateString = crtJSON.getString("pubDate");
                        DateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
                        Date date = df.parse(dateString);
                        System.out.println(result);
                        News crtNews = new News(i,date,title,text,source.toString().toLowerCase(),link);     // TODO change the way of assigning id
                        result.add(crtNews);
                    }
                } catch (JSONException e) {
                    throw new NewsParseException(e.getMessage());
                } catch (ParseException e){
                    throw new NewsParseException(e.getMessage());
                }
                break;
            default:
                throw new IllegalStateException("Undefined source was given");
                //break;
        }
        // TODO news parsing
        return result;
    }
}
