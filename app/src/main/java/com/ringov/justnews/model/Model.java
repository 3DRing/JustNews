package com.ringov.justnews.model;

import com.ringov.justnews.internet.ClientCallback;
import com.ringov.justnews.internet.InternetService;
import com.ringov.justnews.internet.Service;
import com.ringov.justnews.presenter.PresenterInModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Сергей on 26.12.2016.
 */

public class Model implements NewsModel {

    private InternetService service;
    private PresenterInModel<NewsData> presenter;

    private List<News> news;

    public Model(PresenterInModel<NewsData> presenter) {
        this.presenter = presenter;
        this.service = Service.getInstance();

        this.news = new LinkedList<>();
    }

    private List<News> getNewsFromDB() {
        return new LinkedList<>();
    }

    private void getNewsFromInternet() {
        this.service.getData(new ClientCallback() {
            @Override
            public void onResponseSuccess(JSONObject json) throws JSONException {
                try {
                    news = parseNews(json);
                } catch (NewsParseException e) {
                    presenter.newsParsingFailed(e.getMessage());
                }
                presenter.response(getNextNewsFromRAM());
            }

            @Override
            public void onResponseFailure(Throwable t) {
                presenter.responseFailed(t.getMessage());
            }

            @Override
            public void onParseFailure(JSONException e) {
                presenter.jsonParsingFailed(e.getMessage());
            }
        });
    }

    private List<News> parseNews(JSONObject json) throws NewsParseException {

        // TODO news parsing

        return null;
    }

    private NewsData getNextNewsFromRAM() {
        NewsData nextNews = news.get(0);
        news.remove(0);
        return nextNews;
    }

    @Override
    public void requestNext() {
        if (news.size() == 0) {
            getNewsFromInternet();
        } else {
            this.presenter.response(getNextNewsFromRAM());
        }
    }

    @Override
    public void requestHistory() {

        // not implemented

    }
}
