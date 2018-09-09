package com.syl.myapplication1.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.syl.myapplication1.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author   Bright
 * date     2018/9/9 11:45
 * desc
 * RecyclerView 多条目类型,上拉加载更多,条目点击事件
 */
public class RV3Activity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.rv)
    RecyclerView mRv;
    @Bind(R.id.swipe_rl)
    SwipeRefreshLayout mSwipeRl;
    private MultipleAdapter mAdapter;
    private Handler mHandler = new Handler();
    //delayMillis
    private static final int DELAY_MILLIS = 1500;
    private int mShowType = 0;//显示类型

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv3);
        ButterKnife.bind(this);

        mSwipeRl.setColorSchemeResources(R.color.colorAccent);
        mSwipeRl.setOnRefreshListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRv.setLayoutManager(layoutManager);

        mAdapter = new MultipleAdapter(getData(100));
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(RV3Activity.this, position + "--was clicked-----", Toast.LENGTH_SHORT).show();
            }
        });
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mShowType++;
                if (mShowType == 2) {//根据加载状态判断,显示加载那种情况
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.loadMoreFail();
                        }
                    }, DELAY_MILLIS);
                } else if (mShowType >= 4) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.loadMoreEnd();//没有加载更多
                        }
                    });
                } else {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.addData(getData(20));
                            mAdapter.loadMoreComplete();//加载更多,加载完成
                        }
                    }, DELAY_MILLIS);
                }
            }
        });
        mRv.setAdapter(mAdapter);
    }

    private List<MultipleItem> getData(int count) {
        List<MultipleItem> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            if (i % 3 == 0) {
                MultipleItem item = new MultipleItem();
                item.itemType = MultipleItem.HEADER;
                item.name = new Random().nextInt(100)+"";
                list.add(item);

                MultipleItem item2 = new MultipleItem();
                item2.itemType = MultipleItem.CONTENT;
                item2.name = "item content1--" + i+"--"+new Random().nextInt(100);
                list.add(item2);

                MultipleItem item3 = new MultipleItem();
                item3.itemType = MultipleItem.CONTENT;
                item3.name = "item content2--" + i+"--"+new Random().nextInt(100);
                list.add(item3);
            } else if (i % 4 == 0) {
                MultipleItem item = new MultipleItem();
                item.name = new Random().nextInt(100)+"";
                item.itemType = MultipleItem.HEADER;
                list.add(item);

                MultipleItem item2 = new MultipleItem();
                item2.itemType = MultipleItem.CONTENT;
                item2.name = "item content4--" + i+"--"+new Random().nextInt(100);
                list.add(item2);

                MultipleItem item3 = new MultipleItem();
                item3.itemType = MultipleItem.CONTENT;
                item3.name = "item content5--" + i+"--"+new Random().nextInt(100);
                list.add(item3);

                MultipleItem item4 = new MultipleItem();
                item4.itemType = MultipleItem.CONTENT;
                item4.name = "item content6--" + i+"--"+new Random().nextInt(100);
                list.add(item4);
            } else {
                MultipleItem item = new MultipleItem();
                item.name = new Random().nextInt(100)+"";
                item.itemType = MultipleItem.HEADER;
                list.add(item);
            }
        }
        return list;
    }

    /**
     * author   Bright
     * date     2018/9/9 12:12
     * desc
     * 多条目适配器,一定要继承BaseMultiItemQuickAdapter
     */
    class MultipleAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {
        /**
         * Same as QuickAdapter#QuickAdapter(Context,int) but with
         * some initialization data.
         *
         * @param data A new list is created out of this one to avoid mutable list
         */
        public MultipleAdapter(List<MultipleItem> data) {
            super(data);
            addItemType(MultipleItem.HEADER, R.layout.list_item_header);
            addItemType(MultipleItem.CONTENT, R.layout.list_item_content);
        }

        @Override
        protected void convert(BaseViewHolder helper, MultipleItem item) {
            switch (item.getItemType()) {
                case MultipleItem.HEADER:
                    helper.setText(R.id.tv_header, "标题---"+item.name);
                    break;
                case MultipleItem.CONTENT:
                    helper.setText(R.id.tv_content_title, "内容标题");
                    helper.setText(R.id.tv_content_content, item.name);
                    break;
                default:
                    break;
            }
        }
    }

    //刷新
    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mShowType =0;//显示类型
                mAdapter.setNewData(getData(100));//添加刷新后的数据
                mSwipeRl.setRefreshing(false);//停止刷新
            }
        }, DELAY_MILLIS);
    }

    /**
     * author   Bright
     * date     2018/9/9 12:12
     * desc
     * 实现多条目MultiItemEntity实体类接口,设定条目类型
     */
    class MultipleItem implements MultiItemEntity {
        public static final int HEADER = 1;
        public static final int CONTENT = 2;

        public String name;
        public int itemType;

        @Override
        public int getItemType() {
            return itemType;
        }
    }
}
