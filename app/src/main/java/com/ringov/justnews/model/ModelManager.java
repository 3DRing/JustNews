package com.ringov.justnews.model;

import com.ringov.justnews.presenter.PresenterInModel;

/**
 * Created by Сергей on 29.12.2016.
 */

public class ModelManager {
    public static NewsModel getNewsModel(PresenterInModel presenter){
        return new Model(presenter);
    }
}
