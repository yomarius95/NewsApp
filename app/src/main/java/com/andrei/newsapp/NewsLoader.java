package com.andrei.newsapp;

import android.content.Context;
import android.content.AsyncTaskLoader;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

class NewsLoader extends AsyncTaskLoader<List<News>> {
    private static final String LOG_TAG = NewsLoader.class.getSimpleName();

    private String mUrl;

    NewsLoader (Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "Loader start loading");
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        Log.i(LOG_TAG, "Loader in background");

        // Don't perform the request if there are no URLs, or the first URL is null.
        if (TextUtils.isEmpty(mUrl)) {
            return null;
        }
        return QueryUtils.fetchNewsData(mUrl);
    }
}
