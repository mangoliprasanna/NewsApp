package com.example.mango.newsapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopicTabFragment extends Fragment  implements LoaderManager.LoaderCallbacks<String> {
    private RecyclerView topNews;
    public TopicTabFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic_tab, container, false);

        topNews = (RecyclerView) view.findViewById(R.id.topNews);
        topNews.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        topNews.setItemAnimator(new DefaultItemAnimator());

        Bundle bundle = new Bundle();
        bundle.putString("URL", getArguments().getString("URL"));
        getLoaderManager().initLoader(1, bundle, this);
        return view;
    }

    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {
        return new JSONLoader(getContext(), bundle.getString("URL"));
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String s) {
        ArrayList<News> allNews = new News().getAllNewsData(s);
        NewsRecyclerViewAdapter adapter = new NewsRecyclerViewAdapter(allNews, getContext());
        Log.w("NEWSSITE", String.valueOf(allNews.size()));
        topNews.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

}