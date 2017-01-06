package com.ringov.justnews.internet;

/**
 * Created by Сергей on 26.12.2016.
 */

public interface InternetConnection {
    void getData(ClientCallback callback);
    void cancel();
}
