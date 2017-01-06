package com.ringov.justnews.internet;

/**
 * Created by Сергей on 30.12.2016.
 */

public class InternetManager {
    public static InternetConnection getInternetService(){
        return Connection.getInstance();
    }
}
