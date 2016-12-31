package com.ringov.justnews.db;

import android.content.Context;

import com.ringov.justnews.model.News;

/**
 * Created by Сергей on 30.12.2016.
 */

public interface DB {
    String getCrtUrl(Context context);
    void setCrtUrl(Context context, String url);
    boolean containsNews(News news);
    void addNews(News news);

    void clear();
}
