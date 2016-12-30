package com.ringov.justnews.db;

/**
 * Created by Сергей on 30.12.2016.
 */

public class DBManager {
    public static DB getDB(){
        return Database.getInstance();
    }
}
