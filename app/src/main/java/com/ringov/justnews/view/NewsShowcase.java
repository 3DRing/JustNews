package com.ringov.justnews.view;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ringov.justnews.R;
import com.ringov.justnews.model.NewsData;

/**
 * Created by Сергей on 29.12.2016.
 */

class NewsShowcase {

    private Activity activity;

    private TextView title;
    private TextView text;
    private TextView date;
    private TextView source;

    public NewsShowcase(Activity activity){

        this.activity = activity;

        title = (TextView) activity.findViewById(R.id.tvTitle);
        text = (TextView) activity.findViewById(R.id.tvDescription);
        date = (TextView) activity.findViewById(R.id.tvDate);
        source = (TextView) activity.findViewById(R.id.tvSource);
    }

    public void showNews(final NewsData news){
        title.setText(news.getTitle());
        text.setText(news.getText());
        date.setText(news.getFormattedDate());
        source.setText(news.getSource());

        source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUrl(news.getUrl());
            }
        });

    }

    private void openUrl(String url) {
        // TODO open url in browser
        Toast.makeText(activity, url, Toast.LENGTH_SHORT).show();
    }
}