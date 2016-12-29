package com.ringov.justnews.model;

import java.util.Date;

/**
 * Created by Сергей on 26.12.2016.
 */

public interface NewsData {
    int getId();
    Date getDate();
    String getTitle();
    String getText();
    String getSource();
    String getUrl();
    String getFormattedDate();
}
