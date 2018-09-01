package com.example.mango.newsapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final Fragment topNewsFragment = new TopNewsFragment();
    final Fragment headlineFragment = new TopicFragment();
    final FragmentManager fm = getSupportFragmentManager();
    private FrameLayout frameLayout;
    Fragment fragment = null;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_topnews:
                    fm.beginTransaction().hide(fragment).show(topNewsFragment).commit();
                    fragment = topNewsFragment;
                    return true;
                case R.id.navigation_headlines:
                    fm.beginTransaction().hide(fragment).show(headlineFragment).commit();
                    fragment = headlineFragment;
                    return true;
            }
            return false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm.beginTransaction().add(R.id.frameLayout, headlineFragment, "2").hide(headlineFragment).commit();
        fm.beginTransaction().add(R.id.frameLayout,topNewsFragment, "1").commit();

        fragment = topNewsFragment;
        getSupportActionBar().hide();
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
