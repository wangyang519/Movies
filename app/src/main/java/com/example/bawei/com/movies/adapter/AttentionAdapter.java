package com.example.bawei.com.movies.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.bawei.com.movies.fragment.CinemaFragment;
import com.example.bawei.com.movies.fragment.MovieFragment;

import java.util.ArrayList;
import java.util.List;

public class AttentionAdapter extends FragmentPagerAdapter {

    private List<Fragment> mList;

    public AttentionAdapter(FragmentManager fm) {
        super(fm);
        mList = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int i) {
        return mList.get(i);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    public void addFragment(){
        mList.add(new MovieFragment());
        mList.add(new CinemaFragment());
    }

    public List<Fragment> getList() {

        return mList;
    }
}
