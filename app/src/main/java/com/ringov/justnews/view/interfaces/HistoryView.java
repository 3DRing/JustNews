package com.ringov.justnews.view.interfaces;

import com.ringov.justnews.model.NewsData;

import java.util.List;

/**
 * Created by Сергей on 29.12.2016.
 */

public interface HistoryView extends BaseView {
    void showHistory(List<NewsData> data);
}
