package com.syl.googleplay3.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.syl.googleplay3.base.BaseFragment;
import com.syl.googleplay3.base.LoadingPager;
import com.syl.googleplay3.protocol.HotProtocol;
import com.syl.googleplay3.utils.UIUtils;
import com.syl.googleplay3.view.FlowLayout;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by Bright on 2018/7/30.
 *
 * @Describe 热门
 * @Called
 */

public class HotFragment extends BaseFragment {

    private List<String> mHotData;

    /**
     * 决定成功视图长什么样子,为成功视图绑定数据
     *
     * @return
     */
    @Override
    public View initSuccessView() {
        ScrollView scrollView = new ScrollView(UIUtils.getContext());
        FlowLayout flowLayout = new FlowLayout(UIUtils.getContext());
        for (int i = 0; i < mHotData.size(); i++) {
            TextView tv = new TextView(UIUtils.getContext());
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
            final String data = mHotData.get(i);
            tv.setText(data);
            tv.setTextColor(Color.WHITE);
            int padding = UIUtils.dp2px(5);
            tv.setPadding(padding,padding,padding,padding);
            tv.setGravity(Gravity.CENTER);
            flowLayout.addView(tv);

            GradientDrawable backgroundNormal = new GradientDrawable();
            backgroundNormal.setCornerRadius(10);
            Random random = new Random();
            final int alpha = random.nextInt(150) + 50;
            final int red = random.nextInt(150) + 50;
            final int green = random.nextInt(150) + 50;
            final int blue = random.nextInt(150) + 50;
            final int argb = Color.argb(alpha,red,green,blue);
            backgroundNormal.setColor(argb);

            final GradientDrawable backgroundPress = new GradientDrawable();
            backgroundPress.setCornerRadius(10);
            backgroundPress.setColor(Color.GRAY);
            //代码设置selector背景颜色
            StateListDrawable selectorBg = new StateListDrawable();
            selectorBg.addState(new int[]{-android.R.attr.state_pressed}, backgroundNormal);//未按下时的颜色,状态为false
            selectorBg.addState(new int[]{android.R.attr.state_pressed}, backgroundPress);//按下时的颜色,状态为true
            tv.setBackgroundDrawable(selectorBg);

            tv.setClickable(true);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UIUtils.getContext(), data, Toast.LENGTH_SHORT).show();
                }
            });
        }
        scrollView.addView(flowLayout);
        return scrollView;
    }

    /**
     * 在子线程中加载数据,外界触发加载时调用
     *
     * @return
     */
    @Override
    public LoadingPager.LoadResult initData() {
        try {
            HotProtocol protocol = new HotProtocol();
            mHotData = protocol.loadData(0);
            return checkResData(mHotData);
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.LoadResult.ERROR;
        }
    }
}
