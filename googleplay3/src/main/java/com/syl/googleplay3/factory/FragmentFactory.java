package com.syl.googleplay3.factory;

import android.util.SparseArray;

import com.syl.googleplay3.base.BaseFragment;
import com.syl.googleplay3.fragment.AppFragment;
import com.syl.googleplay3.fragment.CategoryFragment;
import com.syl.googleplay3.fragment.GameFragment;
import com.syl.googleplay3.fragment.HomeFragment;
import com.syl.googleplay3.fragment.HotFragment;
import com.syl.googleplay3.fragment.RecommendFragment;
import com.syl.googleplay3.fragment.SubjectFragment;

/**
 * Created by Bright on 2018/7/30.
 *
 * @Describe Fragment的工厂类,提供Fragment的工厂对象
 * @Called
 */

public class FragmentFactory {
    public static final int FRAGMENT_HOME = 0;
    public static final int FRAGMENT_APP = 1;
    public static final int FRAGMENT_GAME = 2;
    public static final int FRAGMENT_SUBJECT = 3;
    public static final int FRAGMENT_RECOMMEND = 4;
    public static final int FRAGMENT_CATEGORY = 5;
    public static final int FRAGMENT_HOT = 6;
    //    public static Map<Integer, BaseFragment> mCatchFragment = new HashMap<>();
    public static SparseArray<BaseFragment> mCatchFragment = new SparseArray<>();

    public static BaseFragment createFragment(int position) {
        BaseFragment fragment;
        //如果集合里面有需要的Fragment,优先从Fragment里面取出
        /*if (mCatchFragment.containsKey(position)) {
            fragment = mCatchFragment.get(position);
            return fragment;
        }*/
        fragment = mCatchFragment.get(position);
        if (fragment != null) {
            return fragment;
        }
        switch (position) {
            case FRAGMENT_HOME://首页
                fragment = new HomeFragment();
                break;
            case FRAGMENT_APP://应用
                fragment = new AppFragment();
                break;
            case FRAGMENT_GAME://游戏
                fragment = new GameFragment();
                break;
            case FRAGMENT_SUBJECT://专题
                fragment = new SubjectFragment();
                break;
            case FRAGMENT_RECOMMEND:
                fragment = new RecommendFragment();//推荐
                break;
            case FRAGMENT_CATEGORY://分类
                fragment = new CategoryFragment();
                break;
            case FRAGMENT_HOT://热门
                fragment = new HotFragment();
                break;
            default:
                break;
        }
        //在返回之前,保存Fragment到集合中
        mCatchFragment.put(position, fragment);
        return fragment;
    }
}
