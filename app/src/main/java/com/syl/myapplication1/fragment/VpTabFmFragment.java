package com.syl.myapplication1.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syl.myapplication1.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/9/14.
 *
 * @Describe ViewPager+TabLayout+Fragment
 * @Called
 */
public class VpTabFmFragment extends BaseFragment {
    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.vp)
    ViewPager mVp;
    private List<String> mTitles;
    private List<Fragment> mFragments;

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public View initView() {
        View rootView = View.inflate(getActivity(), R.layout.fragment_vp_tab_fm, null);
        ButterKnife.bind(this, rootView);
        mTitles = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            mTitles.add("title" + i);
        }
        mFragments = new ArrayList<>();
        for (int i = 0; i < mTitles.size(); i++) {
            mFragments.add(FragmentContentVp.newInstance(mTitles.get(i)));
        }
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0; i < mFragments.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mTitles.get(i)));
        }
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles.get(position);
            }
        };
        mVp.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mVp);
        return rootView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
