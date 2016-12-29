package com.ringov.justnews.presenter;

import com.ringov.justnews.model.BaseModel;
import com.ringov.justnews.model.ModelManager;
import com.ringov.justnews.model.News;
import com.ringov.justnews.view.interfaces.BaseView;

/**
 * Created by Сергей on 29.12.2016.
 */

public abstract class Presenter<ViewType extends BaseView, ModelType extends BaseModel, DataType> implements PresenterInModel<DataType>, PresenterInView {

    protected ViewType view;
    protected ModelType model;

    public Presenter(ViewType view){
        this.view = view;
    }

}
