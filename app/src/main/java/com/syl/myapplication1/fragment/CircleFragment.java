package com.syl.myapplication1.fragment;

import android.annotation.TargetApi;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.WindowManager;
import android.widget.ImageView;

import com.syl.myapplication1.R;

/**
 * Created by Bright on 2018/10/7.
 *
 * @Describe 圆形遮罩
 * @Called
 */
public class CircleFragment extends BaseFragment {
    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ImageView iv = new ImageView(getActivity());
        iv.setScaleType(ImageView.ScaleType.FIT_CENTER);

        iv.setElevation(2f);
        iv.setClipToOutline(true);
        iv.setImageResource(R.mipmap.img2);
        iv.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        iv.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                ImageView iv = (ImageView) view;
                WindowManager windowManager = getActivity().getWindowManager();
                Display defaultDisplay = windowManager.getDefaultDisplay();
                int width = defaultDisplay.getWidth();
                int height = defaultDisplay.getHeight();
                //图片赋值之后的宽高
                int imageWidth = iv.getDrawable().getIntrinsicWidth();
                int imageHeight = iv.getDrawable().getIntrinsicHeight();
                //如果图片的宽高大于屏幕的宽高,就对图片的宽高/2
                while (imageWidth > width || imageHeight > height) {
                    imageWidth >>= 1;
                    imageHeight >>= 1;
                }
                int radius = Math.min(imageWidth, imageHeight) / 2;
                System.out.println("iv.getDrawable().getIntrinsicWidth()==" + imageWidth + "--iv.getDrawable().getIntrinsicHeight()==" + imageHeight);
                int centerX = (view.getRight() - view.getLeft()) / 2;
                int centerY = (view.getBottom() - view.getTop()) / 2;

                //使用圆形遮罩
                outline.setOval(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
            }
        });
        return iv;
    }
}
