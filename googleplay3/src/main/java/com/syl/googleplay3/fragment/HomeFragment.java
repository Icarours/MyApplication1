package com.syl.googleplay3.fragment;

import android.view.View;
import android.widget.ListView;

import com.syl.googleplay3.adapter.HomeAdapter;
import com.syl.googleplay3.base.BaseFragment;
import com.syl.googleplay3.base.LoadingPager;
import com.syl.googleplay3.bean.HomeBean;
import com.syl.googleplay3.bean.ItemInfoBean;
import com.syl.googleplay3.holder.HomePicHolder;
import com.syl.googleplay3.protocol.HomeProtocol;
import com.syl.googleplay3.utils.UIUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by Bright on 2018/7/30.
 *
 * @Describe 首页
 * @Called
 */

public class HomeFragment extends BaseFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private List<ItemInfoBean> mItemInfoBeans;
    private List<String> mPictures;
    private HomeProtocol mHomeProtocol;

    /**
     * 决定成功视图长什么样子,为成功视图绑定数据
     *
     * @return
     */
    @Override
    public View initSuccessView() {
        ListView lv = new ListView(UIUtils.getContext());
        HomeAdapter adapter = new HomeAdapter(lv, mItemInfoBeans, mHomeProtocol);

        //添加轮播图,将ViewPager作为一个条目添加到listView头部
        HomePicHolder homePicHolder = new HomePicHolder();
        homePicHolder.setDataAndRefreshHolderView(mPictures);
        lv.addHeaderView(homePicHolder.mHolderView);//经过了数据绑定的视图

        lv.setAdapter(adapter);
        return lv;
    }


    /**
     * 在子线程中加载数据,外界触发加载数据时调用
     *
     * @return
     */
    @Override
    public LoadingPager.LoadResult initData() {
        try {
            //对网络请求的协议进行封装,将网络请求的重复代码封装到另一个类HomeProtocol中
            mHomeProtocol = new HomeProtocol();
            HomeBean homeBean = mHomeProtocol.loadData(0);
            LoadingPager.LoadResult state = checkResData(homeBean);
            if (state != LoadingPager.LoadResult.SUCCESS) {//如果返回值不是SUCCESS就返回EMPTY
                return LoadingPager.LoadResult.EMPTY;
            }
            state = checkResData(homeBean.getList());
            if (state != LoadingPager.LoadResult.SUCCESS) {
                return LoadingPager.LoadResult.EMPTY;
            }
            //数据正常,把数据保存到成员变量
            mItemInfoBeans = homeBean.getList();
            mPictures = homeBean.getPicture();
            return state;
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.LoadResult.ERROR;
        }
        /*try {
            OkHttpClient okHttpClient = new OkHttpClient();
            String url = Constants.URLS.BASEURL + "home";
            HashMap<String, Object> map = new HashMap<>();
            map.put("index", "" + 0);
            String urlParamsByMap = HttpUtils.getUrlParamsByMap(map);
            url = url + "?" + urlParamsByMap;
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String resJsonStr = response.body().string();
                LogUtils.d(TAG, "resJsonStr==" + resJsonStr);
                Gson gson = new Gson();
                HomeBean homeBean = gson.fromJson(resJsonStr, HomeBean.class);
                LogUtils.d(TAG,"homeBean=="+homeBean);
                *//*if (homeBean==null){
                    return LoadingPager.LoadResult.EMPTY;
                }
                if (homeBean.getList()==null||homeBean.getList().size()==0){
                    return LoadingPager.LoadResult.EMPTY;
                }*//*
                LoadingPager.LoadResult state = checkResData(homeBean);
                if (state != LoadingPager.LoadResult.SUCCESS) {
                    return LoadingPager.LoadResult.EMPTY;
                }
                state = checkResData(homeBean.getList());
                if (state != LoadingPager.LoadResult.SUCCESS) {
                    return LoadingPager.LoadResult.EMPTY;
                }
                //数据正常,把数据保存到尘缘变量
                mItemInfoBeans = homeBean.getList();
                mPictures = homeBean.getPicture();
                return state;
            }
            return LoadingPager.LoadResult.ERROR;
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.LoadResult.ERROR;
        }*/
    }
}
