package com.ringov.justnews.db;

import android.content.Context;

/**
 * Created by Сергей on 30.12.2016.
 */

public class DBManager {
    public static DB getDB(Context context){
        return Database.getInstance(context);
    }
}
