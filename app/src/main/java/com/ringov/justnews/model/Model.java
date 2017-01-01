package com.ringov.justnews.model;

import com.ringov.justnews.db.DB;
import com.ringov.justnews.db.DBManager;
import com.ringov.justnews.internet.ClientCallback;
import com.ringov.justnews.internet.InternetManager;
import com.ringov.justnews.internet.InternetService;
import com.ringov.justnews.internet.SOURCE;
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

    private boolean busy;

    public Model(PresenterInModel<NewsData> presenter) {
        this.presenter = presenter;
        this.service = InternetManager.getInternetService();
        this.db = DBManager.getDB(presenter.getContext());

        this.parser = new NewsParser();
        this.news = new LinkedList<>();

        busy = false;
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

                busy = false;
            }

            @Override
            public void onResponseFailure(Throwable t) {
                presenter.responseFailed(t.getMessage());
                busy = false;
            }

            @Override
            public void onParseFailure(JSONException e) {
                presenter.jsonParsingFailed(e.getMessage());
                busy = false;
            }
        });
    }

    private NewsData getNextNews(){

        return getNotRepeatedNews();
    }

    private NewsData getNotRepeatedNews() {

        News newsToGo = null;
        while(news.size() > 0 && newsToGo == null){
            if(db.containsNews(news.get(0))){
                news.remove(0);
            }else{
                newsToGo = news.get(0);
            }
        }

        if(newsToGo != null) {
            db.addNews(newsToGo);
            // TODO make it in more unified fashion
            db.setCrtUrl(presenter.getContext(), newsToGo.getUrl());
        }

        return newsToGo;
    }

    @Override
    public void requestNext() {
        busy = true;
        if (news.size() == 0) {
            getNewsFromInternet();
        } else {
            this.presenter.response(getNextNews());

            busy = false;
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

    @Override
    public void clearDB() {
        db.clear();
    }

    @Override
    public boolean isBusy() {
        return busy;
    }
}
