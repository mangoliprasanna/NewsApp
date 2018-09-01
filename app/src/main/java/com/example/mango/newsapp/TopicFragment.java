package com.example.mango.newsapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TopicFragment extends Fragment {
    public TopicFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic, container, false);
        View settingsButton = (View) view.findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), SettingsActivity.class);
                getActivity().startActivity(i);
            }
        });

        final String[] topicNames = {
                getActivity().getString(R.string.topic_world),
                getActivity().getString(R.string.topic_science),
                getActivity().getString(R.string.topic_technology),
                getActivity().getString(R.string.topic_sports),
                getActivity().getString(R.string.topic_games),
                getActivity().getString(R.string.topic_music),
                getActivity().getString(R.string.topic_film),
                getActivity().getString(R.string.topic_politics)
        };

        final String[] topicAPIUrl = {
                "https://content.guardianapis.com/search?section=world&api-key=68e66e95-8802-47f1-8eca-3aaf1dcdf17b&show-fields=headline,thumbnail&show-tags=contributor&page-size=30",
                "https://content.guardianapis.com/search?section=science&api-key=68e66e95-8802-47f1-8eca-3aaf1dcdf17b&show-fields=headline,thumbnail&show-tags=contributor&page-size=30",
                "https://content.guardianapis.com/search?section=technology&api-key=68e66e95-8802-47f1-8eca-3aaf1dcdf17b&show-fields=headline,thumbnail&show-tags=contributor&page-size=30",
                "https://content.guardianapis.com/search?section=sport&api-key=68e66e95-8802-47f1-8eca-3aaf1dcdf17b&show-fields=headline,thumbnail&show-tags=contributor&page-size=30",
                "https://content.guardianapis.com/search?section=games&api-key=68e66e95-8802-47f1-8eca-3aaf1dcdf17b&show-fields=headline,thumbnail&show-tags=contributor&page-size=30",
                "https://content.guardianapis.com/search?section=music&api-key=68e66e95-8802-47f1-8eca-3aaf1dcdf17b&show-fields=headline,thumbnail&show-tags=contributor&page-size=30",
                "https://content.guardianapis.com/search?section=film&api-key=68e66e95-8802-47f1-8eca-3aaf1dcdf17b&show-fields=headline,thumbnail&show-tags=contributor&page-size=30",
                "https://content.guardianapis.com/search?section=politics&api-key=68e66e95-8802-47f1-8eca-3aaf1dcdf17b&show-fields=headline,thumbnail&show-tags=contributor&page-size=30"
        };


        ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(getChildFragmentManager());
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        for(int i = 0; i < topicNames.length; i++)
        {
            TopicTabFragment topicTabFragment = new TopicTabFragment();
            Bundle bundle = new Bundle();
            bundle.putString("URL", topicAPIUrl[i]);
            topicTabFragment.setArguments(bundle);
            customPagerAdapter.add(topicTabFragment, topicNames[i]);
        }
        viewPager.setAdapter(customPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {

        }
    }

    class CustomPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentName = new ArrayList<>();

        public CustomPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        public void add(Fragment fm, String name)
        {
            mFragmentList.add(fm);
            mFragmentName.add(name);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return mFragmentName.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }

}
