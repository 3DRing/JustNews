package com.ringov.justnews.internet.parsing;

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

/**
 * Created by Сергей on 29.12.2016.
 */

public class UnifiedParser {
    public static List<News> parseListOfNews(JSONObject json, String dateFormat) throws ParseException {
        List<News> result = new LinkedList<>();
        JSONArray array = null;
        try {
            String source = json.getJSONObject("rss").getJSONObject("channel").getString("title");
            array = json.getJSONObject("rss")
                    .getJSONObject("channel").getJSONArray("item");

            if (array == null) {
                throw new ParseException("unexpected behaviour while getting news JSON array");
            }
            for (int i = 0; i < array.length(); i++) {
                JSONObject crtJSON = array.getJSONObject(i);
                String title = crtJSON.getString("title");
                String link = crtJSON.getString("link");
                String text = crtJSON.getString("description");
                String dateString = crtJSON.getString("pubDate");
                DateFormat df = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
                Date date = df.parse(dateString);
                News crtNews = new News(i,date,title,text,source,link);     // TODO change the way of assigning id
                result.add(crtNews);
            }
        } catch (JSONException e) {
            throw new ParseException(e.getMessage());
        } catch (java.text.ParseException e){
            throw new ParseException(e.getMessage());
        }

        return result;
    }

    public static String fixString(String str) {
        String result = str
                .replace("&quot;","\"")
                .replace("&quot;","\"");
        return result;
    }
}
