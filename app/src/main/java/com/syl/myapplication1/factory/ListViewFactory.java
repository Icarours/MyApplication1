package com.syl.myapplication1.factory;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ListView;

import com.syl.myapplication1.utils.UIUtils;

/**
 * Created by Bright on 2018/8/1.
 *
 * @Describe
 * @Called
 */

public class ListViewFactory {
    public static ListView createListView() {
        ListView listView = new ListView(UIUtils.getContext());
        //对ListView进行一些常规的设置
        listView.setCacheColorHint(Color.RED);
        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        listView.setDivider(new ColorDrawable(Color.GREEN));
        listView.setHorizontalScrollBarEnabled(true);
        listView.setFastScrollEnabled(true);
        return listView;
    }
}
