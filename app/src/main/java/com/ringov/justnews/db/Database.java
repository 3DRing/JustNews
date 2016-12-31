package com.ringov.justnews.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.ringov.justnews.model.News;

/**
 * Created by Сергей on 30.12.2016.
 */

public class Database implements DB{


    private static Database instance;

    public static Database getInstance(Context context) {
        if (instance != null) {
            return instance;
        } else {
            instance = new Database(context);
            return instance;
        }
    }

    private DBHandler handler;

    private final String DB_SP_ID = "com.ringov.justnews.dbsp";
    private final String SP_CRT_URL_ID = "com.ringov.justnews.dbsp.crturl";
    private SharedPreferences sp;

    private Database(Context context){
        handler = new DBHandler(context);
    }

    @Override
    public String getCrtUrl(Context context) {
        sp = context.getSharedPreferences(DB_SP_ID, Context.MODE_PRIVATE);
        String url = sp.getString(SP_CRT_URL_ID, "");
        return url;
    }

    @Override
    public void setCrtUrl(Context context, String url) {
        sp = context.getSharedPreferences(DB_SP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SP_CRT_URL_ID, url);
        editor.commit();
    }

    @Override
    public boolean containsNews(News news) {
        long crtHash = news.getHash();
        return handler.containsHash(crtHash);
    }

    @Override
    public void addNews(News news) {
        handler.addHash(news.getHash());
    }

    @Override
    public void clear() {
        handler.clearHashTable();
    }
}
