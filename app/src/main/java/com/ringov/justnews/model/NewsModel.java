package com.ringov.justnews.model;

import android.content.Context;

/**
 * Created by Сергей on 26.12.2016.
 */

public interface NewsModel extends BaseModel{
    void requestNext();
    void requestHistory();
    String getCrtUrl();

    void clearDB();
}
