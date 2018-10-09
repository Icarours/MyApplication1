package com.syl.mobileplayer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.syl.mobileplayer.ui.fragment.BaseFragment;

import java.util.ArrayList;

/**
 * Created by ThinkPad on 2016/4/11.
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter {
    ArrayList<BaseFragment> fragments;
    public MainViewPagerAdapter(FragmentManager fm,ArrayList<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
