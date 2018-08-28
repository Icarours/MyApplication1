package com.syl.googleplay3.adapter;

import android.os.SystemClock;
import android.widget.AbsListView;

import com.syl.googleplay3.base.BaseProtocol;
import com.syl.googleplay3.bean.ItemInfoBean;

import java.util.List;

/**
 * Created by Bright on 2018/8/1.
 *
 * @Describe
 * @Called
 */

public class GameAdapter extends ItemAdapter {
    private List<ItemInfoBean> dataSet;
    private BaseProtocol<List<ItemInfoBean>> protocol;
    public GameAdapter(AbsListView absListView, List<ItemInfoBean> dataSet, BaseProtocol protocol) {
        super(absListView, dataSet, protocol);
        this.dataSet = dataSet;
        this.protocol = protocol;
    }

    @Override
    public List<ItemInfoBean> onLoadMoreData() throws Exception {
        SystemClock.sleep(1000);
        return protocol.loadData(dataSet.size());
    }
}
