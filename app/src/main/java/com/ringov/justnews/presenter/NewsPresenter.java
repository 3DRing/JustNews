package com.ringov.justnews.presenter;

import android.content.Context;

import com.ringov.justnews.model.ModelManager;
import com.ringov.justnews.model.NewsData;
import com.ringov.justnews.model.NewsModel;
import com.ringov.justnews.view.interfaces.AndroidDependence;
import com.ringov.justnews.view.interfaces.SingleNewsView;

import java.util.List;

/**
 * Created by Сергей on 29.12.2016.
 */

public class NewsPresenter extends Presenter<SingleNewsView, NewsModel, NewsData> {

    public NewsPresenter(SingleNewsView view) {
        super(view);
    }

    public NewsPresenter(SingleNewsView view, AndroidDependence dependence){
        super(view,dependence);
    }

    @Override
    protected void initializeModel() {
        this.model = ModelManager.getNewsModel(this);
    }

    @Override
    public void response(NewsData data) {
        setLoading(false);
        if(data != null){
            this.view.showNews(data);
        }
    }

    @Override
    public void responseHistory(List<NewsData> data) {
        setLoading(false);
    }

    @Override
    public void newsParsingFailed(String message) {
        setLoading(false);
        view.showMessage(message);
    }

    @Override
    public void responseFailed(String message) {
        setLoading(false);
        view.showMessage(message);
    }

    @Override
    public void jsonParsingFailed(String message) {
        setLoading(false);
        view.showMessage(message);
    }

    @Override
    public Context getContext() {
        Context context = dependence == null ? null : dependence.getContext();
        if(context == null){
            // define more unified way of dealing with exceptions
            throw new IllegalStateException("context was not given");
        }
        return context;
    }

    @Override
    public void isBusy() {

    }

    @Override
    public void requestNext() {
        setLoading(true);
        this.model.requestNext();
    }

    @Override
    public void requestHistory() {
        setLoading(true);
        this.model.requestHistory();
    }

    @Override
    public void openCrtUrl() {
        String url = this.model.getCrtUrl();
        if(!url.equals("")) {
            view.openUrl(url);
        }
    }

    @Override
    public void clearDB() {
        model.clearDB();
    }
}
