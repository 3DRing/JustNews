package com.ringov.justnews.model;

import java.util.Date;

/**
 * Created by Сергей on 26.12.2016.
 */

public class News implements NewsData{

    public static final News NULL = new News();

    private boolean nil;

    private int id;
    private Date date;
    private String title;
    private String text;
    private String source;
    private String url;

    public News(){
        nil = true;
    }

    public boolean isNull(){
        return nil;
    }

    @Override
    public int getId() {
        return id;
    }
    @Override
    public Date getDate() {
        return date;
    }
    @Override
    public String getTitle() {
        return title;
    }
    @Override
    public String getText() {
        return text;
    }
    @Override
    public String getSource() {
        return source;
    }
    @Override
    public String getUrl() {
        return url;
    }
}
