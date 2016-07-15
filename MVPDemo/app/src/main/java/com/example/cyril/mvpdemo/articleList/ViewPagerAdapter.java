package com.example.cyril.mvpdemo.articleList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Cyril on 2016/7/15.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private static final int COUNT = 2;
    private String[] titles = new String[]{"Andoid", "iOS"};
    private String[] url = new String[]{"Android", "iOS"};

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ArticleListFragment.newInstance(url[position]);
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
