package com.ringov.justnews.presenter;

import android.content.Context;

import com.ringov.justnews.model.News;
import com.ringov.justnews.model.NewsData;

import java.util.List;

/**
 * Created by Сергей on 26.12.2016.
 */

public interface PresenterInModel<DataType> {
    void response(DataType data);
    void responseHistory(List<DataType> data);

    void newsParsingFailed(String message);

    void responseFailed(String message);

    void jsonParsingFailed(String message);

    Context getContext();
}
