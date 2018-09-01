package com.example.mango.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsHolder> {

    private List<News> allNews;
    private Context context;
    NewsRecyclerViewAdapter(List<News> allNews, Context context){
        this.allNews = allNews; this.context = context;
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_newsitem, parent, false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, int position) {
        final News currentNews = allNews.get(position);
        holder.newsTimeTextView.setText(currentNews.getNewsDate());
        if(currentNews.getNewsHeadLine().length() > 60)
            holder.newsHeadlineTextView.setText(currentNews.getNewsHeadLine().substring(0, 60).toString() + "...");
        else
            holder.newsHeadlineTextView.setText(currentNews.getNewsHeadLine());
        holder.newsCategoryTextView.setText(context.getString(R.string.news_author_prifix) + " " + currentNews.getNewsAuthor());
        if(currentNews.getNewsThumbline() != null)
            Picasso.get().load(currentNews.getNewsThumbline()).into(holder.newsImageView);

        holder.listContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentNews.getNewsWebUrl()));
                context.startActivity(browserIntent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return allNews.size();
    }

    class NewsHolder extends RecyclerView.ViewHolder{
        TextView newsHeadlineTextView;
        TextView newsTimeTextView;
        ImageView newsImageView;
        TextView newsCategoryTextView;
        View listContainer;
        public NewsHolder(View itemView) {
            super(itemView);
            newsImageView = (ImageView) itemView.findViewById(R.id.newsImage);
            newsHeadlineTextView = (TextView) itemView.findViewById(R.id.mewsHeadline);
            newsTimeTextView = (TextView) itemView.findViewById(R.id.newsTime);
            listContainer = (View) itemView.findViewById(R.id.listContainer);
            newsCategoryTextView = (TextView) itemView.findViewById(R.id.newsAuthor);
        }
    }
}
