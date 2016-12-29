package com.ringov.justnews.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ringov.justnews.R;
import com.ringov.justnews.model.NewsData;
import com.ringov.justnews.presenter.NewsPresenter;
import com.ringov.justnews.presenter.PresenterInView;
import com.ringov.justnews.view.interfaces.SingleNewsView;

public class MainActivity extends AppCompatActivity implements SingleNewsView {

    private PresenterInView presenter;

    private Button btnAddWidget;

    private NewsShowcase showcase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new NewsPresenter(this);
        showcase = new NewsShowcase(this);

        this.btnAddWidget = (Button) findViewById(R.id.btnAddWidget);
        this.btnAddWidget.setOnClickListener(new View.OnClickListener() {
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
}
