package com.ringov.justnews.model;

import android.content.Context;

import com.ringov.justnews.db.DB;
import com.ringov.justnews.db.DBManager;
import com.ringov.justnews.db.Database;
import com.ringov.justnews.internet.ClientCallback;
import com.ringov.justnews.internet.InternetManager;
import com.ringov.justnews.internet.InternetService;
import com.ringov.justnews.internet.SOURCE;
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
    private DB db;
    private PresenterInModel<NewsData> presenter;

    private Parser<News,SOURCE> parser;
    private List<News> news;

    public Model(PresenterInModel<NewsData> presenter) {
        this.presenter = presenter;
        this.service = InternetManager.getInternetService();
        this.db = DBManager.getDB();

        this.parser = new NewsParser();
        this.news = new LinkedList<>();
    }

    private List<News> getNewsFromDB() {
        return new LinkedList<>();
    }

    private void getNewsFromInternet() {
        this.service.getData(new ClientCallback() {
            @Override
            public void onResponseSuccess(JSONObject json, SOURCE source) throws JSONException {
                try {
                    news = parser.parse(json, source);
                } catch (NewsParseException e) {
                    presenter.newsParsingFailed(e.getMessage());
                }
                presenter.response(getNextNews());
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


    /**
     * Logic of choosing what source of news should be used
     *
     * @return
     */
    private NewsData getNextNews(){
        NewsData crtNews = getNextNewsFromRAM();

        // TODO make it in more unified fashion
        db.setCrtUrl(presenter.getContext(), crtNews.getUrl());

        return crtNews;
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
            this.presenter.response(getNextNews());
        }
    }

    @Override
    public void requestHistory() {

        // not implemented

    }

    @Override
    public String getCrtUrl() {
        return db.getCrtUrl(presenter.getContext());
    }
}
