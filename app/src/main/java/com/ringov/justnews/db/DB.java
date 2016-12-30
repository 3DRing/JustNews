package com.ringov.justnews.db;

import android.content.Context;

/**
 * Created by Сергей on 30.12.2016.
 */

public interface DB {
    String getCrtUrl(Context context);
    void setCrtUrl(Context context, String url);
}
