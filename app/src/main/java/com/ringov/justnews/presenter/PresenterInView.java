package com.ringov.justnews.presenter;

/**
 * Created by Сергей on 29.12.2016.
 */

public interface PresenterInView {
    void requestNext();
    void requestHistory();
    void openCrtUrl();

    void clearDB();
}
