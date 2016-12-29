package com.ringov.justnews.view.interfaces;

import com.ringov.justnews.model.NewsData;

/**
 * Created by Сергей on 29.12.2016.
 */

public interface SingleNewsView extends BaseView{
    void showNews(NewsData data);
}
