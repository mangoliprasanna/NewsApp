package com.example.mango.newsapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;


public class TopNewsFragment extends Fragment implements LoaderManager.LoaderCallbacks<String>{

    private final String REQUEST_URL = "https://content.guardianapis.com/search?api-key=68e66e95-8802-47f1-8eca-3aaf1dcdf17b&show-fields=headline,thumbnail&show-tags=contributor";

    private RecyclerView topNews;
    private ProgressBar progress;
    private TextView errorTextView;
    public TopNewsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_news, container, false);
        View settingsButton = (View) view.findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), SettingsActivity.class);
                getActivity().startActivity(i);
            }
        });


        topNews = (RecyclerView) view.findViewById(R.id.topNews);
        errorTextView = (TextView) view.findViewById(R.id.errorMessage);
        progress = (ProgressBar) view.findViewById(R.id.progressBar);

        topNews.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        topNews.setItemAnimator(new DefaultItemAnimator());

        if(isNetworkConnected()) {
            progress.setVisibility(View.VISIBLE);

            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
            String maxNewsPerPage = sharedPrefs.getString(getString(R.string.settings_max_news_key), getString(R.string.settings_max_news_default));
            String orderBy = sharedPrefs.getString(getString(R.string.settings_order_by_key), getString(R.string.settings_order_by_default));

            Uri.Builder uriBuilder = Uri.parse(REQUEST_URL).buildUpon();
            uriBuilder.appendQueryParameter("order-by", orderBy);
            uriBuilder.appendQueryParameter("page-size", maxNewsPerPage);

            Bundle bundle = new Bundle();
            bundle.putString("URL", uriBuilder.toString());
            getLoaderManager().initLoader(1, bundle, this);

        } else {
            errorTextView.setVisibility(View.VISIBLE);
            errorTextView.setText(R.string.no_internet_msg);
        }
        return view;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {
        return new JSONLoader(getContext(), bundle.getString("URL"));
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String s) {
        progress.setVisibility(View.GONE);
        ArrayList<News> allNews = new News().getAllNewsData(s);
        if(allNews.size() == 0)
            errorTextView.setVisibility(View.VISIBLE);

        NewsRecyclerViewAdapter adapter = new NewsRecyclerViewAdapter(allNews, getContext());
        topNews.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
