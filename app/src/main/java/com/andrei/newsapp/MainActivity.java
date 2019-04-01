package com.andrei.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>  {

    public final static String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int NEWS_LOADEER_ID = 1;
    private static final long ONE_WEEK = 604800000;
    private static final String GUARDIAN_REQUEST_URL = "https://content.guardianapis.com/search?q=europe&format=json&tag=politics/politics&use-date=first-publication&show-tags=contributor&show-fields=headline,thumbnail,short-url&order-by=relevance&show-references=author&page-size=20&order-by=newest&api-key=test";

    private NewsAdapter adapter;
    private Date currentDate;

    @BindView(R.id.list) ListView newsListView;
    @BindView(R.id.empty_view) TextView emptyTextView;
    @BindView(R.id.loading_spinner) ProgressBar loadingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        currentDate = new Date(System.currentTimeMillis() - ONE_WEEK);

        newsListView.setEmptyView(emptyTextView);

        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        adapter = new NewsAdapter(this, new ArrayList<News>());

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News currentNews = adapter.getItem(position);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentNews.getArticleUrl()));
                startActivity(intent);
            }
        });

        newsListView.setAdapter(adapter);

        if(isConnected) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADEER_ID, null, this);
        } else {
            loadingSpinner.setVisibility(View.GONE);
            emptyTextView.setText(R.string.no_internet);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("from-date", sdfDate.format(currentDate));
        Log.i(LOG_TAG, uriBuilder.toString());

        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        adapter.clear();

        if(news != null && !news.isEmpty()) {
            adapter.addAll(news);
        }

        emptyTextView.setText(R.string.no_news);
        loadingSpinner.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        adapter.clear();
    }
}
