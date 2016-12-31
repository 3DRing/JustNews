package com.ringov.justnews.model;

import com.ringov.justnews.Utils;

import java.text.SimpleDateFormat;
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
    private int hash;

    public News(){
        nil = true;
    }

    public News(int id, Date date, String title, String text, String source, String url){
        this.id = id;
        this.date = date;
        this.title = title;
        this.text = text;
        this.source = source;
        this.url = url;

        this.nil = false;
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


    /**
     * Format as follows: dd.mm.yy, HH.mm
     *
     * @return
     */
    @Override
    public String getFormattedDate() {
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        String resultString;
        if(sdf.format(today).equals(sdf.format(date))){
            sdf = new SimpleDateFormat("HH.mm");
            resultString = "Сегодня, в " + sdf.format(date);
        }else{
            sdf = new SimpleDateFormat("dd.mm.yy, HH.mm");
            resultString = sdf.format(date);
        }

        return resultString;
    }

    public long getHash() {
        int hash = 0;

        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(getUrl()));

        return Utils.hash(sb.toString());
    }
}
