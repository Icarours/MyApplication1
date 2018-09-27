package com.syl.myapplication1.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.syl.myapplication1.R;
import com.syl.myapplication1.fragment.FragmentContentVp;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author   Bright
 * date     2018/9/13 23:30
 * desc
 * ViewPager+tabLayout+Fragment
 */
public class VpActivity extends AppCompatActivity {

    @Bind(R.id.tabs)
    TabLayout mTabs;
    @Bind(R.id.vp_view)
    ViewPager mVpView;
    private List<String> mListTitle;
    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vp);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        mListTitle = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            mListTitle.add("title"+i);
        }
        mFragments = new ArrayList<>();
        for (int i = 0; i < mListTitle.size(); i++) {
            mFragments.add(FragmentContentVp.newInstance(mListTitle.get(i)));
        }
        mTabs.setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0; i < mListTitle.size(); i++) {
            mTabs.addTab(mTabs.newTab().setText(mListTitle.get(i)));
        }
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
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
                return mListTitle.get(position);
            }
        };
        mVpView.setAdapter(adapter);
        mTabs.setupWithViewPager(mVpView);
    }
}
