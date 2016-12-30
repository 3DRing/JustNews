package com.ringov.justnews.view;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.ringov.justnews.R;
import com.ringov.justnews.model.NewsData;
import com.ringov.justnews.presenter.NewsPresenter;
import com.ringov.justnews.presenter.PresenterInView;
import com.ringov.justnews.view.interfaces.AndroidDependence;
import com.ringov.justnews.view.interfaces.SingleNewsView;

/**
 * Created by Сергей on 26.12.2016.
 */

public class JustNewsWidget extends AppWidgetProvider implements SingleNewsView, AndroidDependence{
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    private static final String GET_NEWS = "com.ringov.justnews.widget.get_news";
    private static final String OPEN_URL = "com.ringov.justnews.widget.open_url";

    private PresenterInView presenter;
    private Context context;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        RemoteViews remoteViews;
        ComponentName newsWidget;

        remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout_dark);
        newsWidget = new ComponentName(context, JustNewsWidget.class);

        remoteViews.setOnClickPendingIntent(R.id.llNextNewsClickLayout, getPendingSelfIntent(context, GET_NEWS));
        remoteViews.setOnClickPendingIntent(R.id.llUrlClickLayout, getPendingSelfIntent(context, OPEN_URL));

        appWidgetManager.updateAppWidget(newsWidget, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        String crtAction = intent.getAction();
        boolean getNews = GET_NEWS.equals(crtAction);
        boolean openUrl = OPEN_URL.equals(crtAction);

        if(getNews || openUrl){
            this.context = context;
            this.presenter = new NewsPresenter(this, this);
        }

        // Click on widget to get next news
        if (getNews) {
            this.presenter.requestNext();
        }

        // Click on source to open link
        if(openUrl){
            this.presenter.openCrtUrl();
        }
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void showNews(NewsData data) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        RemoteViews remoteViews;
        ComponentName newsWidget;

        remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout_dark);
        newsWidget = new ComponentName(context, JustNewsWidget.class);

        remoteViews.setTextViewText(R.id.tvTitle, data.getTitle());
        remoteViews.setTextViewText(R.id.tvDescription, data.getText());
        remoteViews.setTextViewText(R.id.tvDate, data.getFormattedDate());
        remoteViews.setTextViewText(R.id.tvSource, data.getSource());

        appWidgetManager.updateAppWidget(newsWidget, remoteViews);
    }

    @Override
    public void openUrl(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(browserIntent);
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public Context getContext() {
        return context;
    }
}
