package com.ringov.justnews.model;

import com.ringov.justnews.db.DB;
import com.ringov.justnews.db.DBManager;
import com.ringov.justnews.internet.ClientCallback;
import com.ringov.justnews.internet.InternetManager;
import com.ringov.justnews.internet.InternetConnection;
import com.ringov.justnews.internet.parsing.SourceParser;
import com.ringov.justnews.presenter.PresenterInModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Сергей on 26.12.2016.
 */

public class Model implements NewsModel {

    private InternetConnection service;
    private DB db;
    private PresenterInModel<NewsData> presenter;

    private List<News> news;

    private boolean busy;

    public Model(PresenterInModel<NewsData> presenter) {
        this.presenter = presenter;
        this.service = InternetManager.getInternetService();
        this.db = DBManager.getDB(presenter.getContext());

        this.news = new LinkedList<>();

        busy = false;
    }

    private void getNewsFromInternet() {
        this.service.getData(new ClientCallback<List<News>>() {
            @Override
            public void onResponseSuccess(JSONObject json, SourceParser<List<News>> parser) throws JSONException {
                try {
                    news = parser.parse(json);
                    presenter.response(getNextNews());
                } catch (ParseException e) {
                    presenter.newsParsingFailed(e.getMessage());
                }
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
