package com.syl.myapplication1.factory;

import android.support.v4.app.Fragment;

import com.syl.myapplication1.fragment.BaseFragment;
import com.syl.myapplication1.fragment.ContentFragment;
import com.syl.myapplication1.fragment.ContentFragment2;
import com.syl.myapplication1.fragment.ContentFragment3;
import com.syl.myapplication1.fragment.ContentFragment4;

import java.util.HashMap;

/**
 * Created by Bright on 2018/7/18.
 *
 * @Describe Fragment的工具类, 复用Fragment.对Fragment进行缓存控制
 * @Called
 */

public class FragmentFactory {
    private static HashMap<String, BaseFragment> mCacheFragment = new HashMap<>();

    public static Fragment createFragment(String fragmentTag) {
        BaseFragment baseFragment = null;
        //如果集合里面有需要的Fragment,优先从Fragment里面取出
        if (mCacheFragment.containsKey(fragmentTag)) {
            return mCacheFragment.get(fragmentTag);
        }
        switch (fragmentTag) {
            case "content_fragment1":
                baseFragment = new ContentFragment();
                break;
            case "content_fragment2":
                baseFragment = new ContentFragment2();
                break;
            case "content_fragment3":
                baseFragment = new ContentFragment3();
                break;
            case "content_fragment4":
                baseFragment = new ContentFragment4();
                break;
            default:
                break;
        }
        return baseFragment;
    }
}
