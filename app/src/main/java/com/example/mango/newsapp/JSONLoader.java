package com.example.mango.newsapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class JSONLoader extends AsyncTaskLoader<String> {

    private final String url;
    public JSONLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        return makeHttpRequest();
    }

    private String makeHttpRequest(){
        String jsonResponse = "";
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {

         httpURLConnection = (HttpURLConnection) new URL(this.url).openConnection();
         httpURLConnection.setRequestMethod("GET");
         httpURLConnection.setConnectTimeout(10000);
         httpURLConnection.setReadTimeout(10000);
         httpURLConnection.connect();

         if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
             inputStream = httpURLConnection.getInputStream();
         }

         StringBuilder outputJson = new StringBuilder();
         if(inputStream != null){
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
             String eachLine = bufferedReader.readLine();
             while(eachLine != null){
                 outputJson.append(eachLine);
                 eachLine = bufferedReader.readLine();
             }
         }
         jsonResponse = outputJson.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }
}
