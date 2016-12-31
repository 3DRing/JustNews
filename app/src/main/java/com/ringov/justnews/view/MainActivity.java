package com.ringov.justnews.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ringov.justnews.R;
import com.ringov.justnews.model.NewsData;
import com.ringov.justnews.presenter.NewsPresenter;
import com.ringov.justnews.presenter.PresenterInView;
import com.ringov.justnews.view.interfaces.AndroidDependence;
import com.ringov.justnews.view.interfaces.SingleNewsView;

public class MainActivity extends AppCompatActivity implements SingleNewsView, AndroidDependence {

    private PresenterInView presenter;

    private Button btnAddWidget;
    private LinearLayout btnOpenUrl;
    private LinearLayout llNextNewsClickLayout;

    private NewsShowcase showcase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new NewsPresenter(this,this);
        showcase = new NewsShowcase(this);

        this.btnAddWidget = (Button) findViewById(R.id.btnAddWidget);
        this.btnAddWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.clearDB();
            }
        });

        btnOpenUrl = (LinearLayout) findViewById(R.id.llNextNewsClickLayout);
        btnOpenUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.openCrtUrl();
            }
        });


        llNextNewsClickLayout = (LinearLayout) findViewById(R.id.llNextNewsClickLayout);
        llNextNewsClickLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.requestNext();
            }
        });
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showNews(NewsData data) {
        showcase.showNews(data);
    }

    @Override
    public void openUrl(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    @Override
    public Context getContext() {
        return this;
    }

    private class NewsShowcase {

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
}
