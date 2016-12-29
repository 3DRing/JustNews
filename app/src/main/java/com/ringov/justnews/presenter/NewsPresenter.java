package com.ringov.justnews.presenter;

import com.ringov.justnews.model.ModelManager;
import com.ringov.justnews.model.NewsData;
import com.ringov.justnews.model.NewsModel;
import com.ringov.justnews.view.interfaces.SingleNewsView;

import java.util.List;

/**
 * Created by Сергей on 29.12.2016.
 */

public class NewsPresenter extends Presenter<SingleNewsView, NewsModel, NewsData> {

    public NewsPresenter(SingleNewsView view) {
        super(view);
        this.model = ModelManager.getTestNewsModel(this);

        loading = false;
    }

    private boolean loading;

    public void setLoading(boolean loading){
        if(loading && this.loading){
            // unexpected behaviour
            throw new IllegalStateException("should not call for loading until previous is finished");
        }
        if(!loading && !this.loading){
            throw new IllegalStateException("loading has not been called - nothing to hide");
        }
        if(loading && !this.loading){
            this.loading = true;
            view.showLoading("loading"); // TODO remove hardcoded text
        }
        if(!loading && this.loading){
            this.loading = false;
            view.hideLoading();
        }
    }

    @Override
    public void response(NewsData data) {
        this.view.showNews(data);
    }

    @Override
    public void responseHistory(List<NewsData> data) {

    }

    @Override
    public void newsParsingFailed(String message) {
        view.showMessage(message);
    }

    @Override
    public void responseFailed(String message) {
        view.showMessage(message);
    }

    @Override
    public void jsonParsingFailed(String message) {

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
        this.model.requestHistory();
    }
}
