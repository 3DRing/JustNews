package com.ringov.justnews.presenter;

import android.support.annotation.Nullable;

import com.ringov.justnews.model.BaseModel;
import com.ringov.justnews.model.ModelManager;
import com.ringov.justnews.model.News;
import com.ringov.justnews.view.interfaces.AndroidDependence;
import com.ringov.justnews.view.interfaces.BaseView;

/**
 * Created by Сергей on 29.12.2016.
 */

public abstract class Presenter<ViewType extends BaseView, ModelType extends BaseModel, DataType> implements PresenterInModel<DataType>, PresenterInView {

    protected AndroidDependence dependence;
    protected ViewType view;
    protected ModelType model;
    protected boolean loading;

    public Presenter(ViewType view){
        this.view = view;

        loading = false;
    }

    public Presenter(ViewType view, AndroidDependence dependence){
        this.view = view;
        this.setAndroidDependence(dependence);

        loading = false;

        initializeModel();
    }

    protected abstract void initializeModel();

    public void setAndroidDependence(AndroidDependence dependence){
        this.dependence = dependence;
    }

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
}
