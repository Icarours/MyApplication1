package com.syl.googleplay3.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.syl.googleplay3.base.BaseFragment;
import com.syl.googleplay3.base.LoadingPager;
import com.syl.googleplay3.protocol.RecommendProtocol;
import com.syl.googleplay3.utils.UIUtils;
import com.syl.googleplay3.view.ShakeListener;
import com.syl.googleplay3.view.flyinout.StellarMap;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by Bright on 2018/7/30.
 *
 * @Describe 推荐
 * @Called
 */

public class RecommendFragment extends BaseFragment {

    private RecommendAdapter mAdapter;
    private ShakeListener mShakeListener;
    private List<String> mRecommendData;

    /**
     * 决定成功视图长什么样子,为成功视图绑定数据
     *
     * @return
     */
    @Override
    public View initSuccessView() {
        final StellarMap stellarMap = new StellarMap(UIUtils.getContext());
        mAdapter = new RecommendAdapter();
        stellarMap.setAdapter(mAdapter);
        //拆分屏幕
        stellarMap.setRegularity(15, 20);
        //默认选择第一项
        stellarMap.setGroup(0, true);
        //加入摇一摇监听
        mShakeListener = new ShakeListener(UIUtils.getContext());
        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                //检测到了摇一摇
                //切换
                int currentGroup = stellarMap.getCurrentGroup();
                if (currentGroup == mAdapter.getGroupCount() - 1) {//如果是第一项,就直接返回第一项
                    currentGroup = 0;
                } else {//否则就返回接下来的一项
                    currentGroup++;
                }
                stellarMap.setGroup(currentGroup, true);
            }
        });
        return stellarMap;
    }

    /**
     * 在子线程中加载数据,外界触发加载时调用
     *
     * @return
     */
    @Override
    public LoadingPager.LoadResult initData() {
        try {
            RecommendProtocol protocol = new RecommendProtocol();
            mRecommendData = protocol.loadData(0);
            return checkResData(mRecommendData);
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.LoadResult.ERROR;
        }
    }

    @Override
    public void onResume() {
        if (mShakeListener != null) {
            mShakeListener.resume();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (mShakeListener != null) {
            mShakeListener.pause();
        }
        super.onPause();
    }

    class RecommendAdapter implements StellarMap.Adapter {
        public static final int PAGESIZE = 15;//每组15个

        @Override
        public int getGroupCount() {//多少个组
            int count = mRecommendData.size();
            if (count %PAGESIZE==0){//如果整除,就返回count;如果没有整除,有余数,就返回count+1
                return mRecommendData.size();
            }
            return count+1;
        }

        @Override
        public int getCount(int group) {//每组多少个
            if (mRecommendData.size()%PAGESIZE==0){//如果正好整除,每组就直接返回PAGESIZE个数据;
                return PAGESIZE;
            }else {//如果不能整除
                if (group==mRecommendData.size()-1){//且是最后一组,就返回其余数
                    return mRecommendData.size()%PAGESIZE;
                }
                return PAGESIZE;//其他组0-mRecommendData.size()-2,返回PAGESIZE个数据
            }
        }

        @Override
        public View getView(int group, int position, View convertView) {
            TextView tv = new TextView(UIUtils.getContext());
            int index = group*PAGESIZE+position;
            tv.setText(mRecommendData.get(index));
            Random random = new Random();
            //随机大小
            tv.setTextSize(random.nextInt(10)+14);
            //随机颜色
            int alpha = random.nextInt(200) + 30;//30-230;
            int red = random.nextInt(200) + 30;//30-230;
            int green = random.nextInt(200) + 30;//30-230;
            int blue = random.nextInt(200) + 30;//30-230;
            int argb = Color.argb(alpha, red, green, blue);
            tv.setTextColor(argb);
            return tv;
        }

        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            return 0;
        }
    }
}
