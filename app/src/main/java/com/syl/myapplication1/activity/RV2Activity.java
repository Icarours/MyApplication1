package com.syl.myapplication1.activity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.syl.myapplication1.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * author   Bright
 * date     2018/9/9 10:58
 * desc
 * RecyclerView条目点击事件,下拉刷新,上拉加载更多..
 */
public class RV2Activity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.rv)
    RecyclerView mRv;
    @Bind(R.id.swipe_rl)
    SwipeRefreshLayout mSwipeRl;
    private Handler mHandler = new Handler();
    //delayMillis
    private static final int DELAY_MILLIS = 1500;

    private int mShowType = 0;
    private Adapter1 mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv2);
        ButterKnife.bind(this);

        mSwipeRl.setColorSchemeResources(R.color.colorAccent);
        mSwipeRl.setOnRefreshListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRv.setLayoutManager(layoutManager);
        mAdapter = new Adapter1(R.layout.list_item_test1, getData());
        mRv.setAdapter(mAdapter);
        mRv.addItemDecoration(new ColorDividerItemDecoration());

        //加载动画
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);

        //设置点击事件
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(RV2Activity.this, position + "----was clicked..", Toast.LENGTH_SHORT).show();
            }
        });
        //设置加载更多
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
                            mAdapter.addData(addDatas());
                            mAdapter.loadMoreComplete();//加载更多,加载完成
                        }
                    }, DELAY_MILLIS);
                }
            }
        });

        //添加RecyclerView头部条目
        addHeaderView();
    }

    private void addHeaderView() {
        View headerView = getLayoutInflater().inflate(R.layout.item_header, null);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mAdapter.addHeaderView(headerView);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"RecyclerView 头部被点击了..",2000);
            }
        });
    }


    public List<String> addDatas() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add("test---add---" + new Random().nextInt(100));
        }
        return list;
    }

    public List<String> getData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("test---" + i);
        }
        return list;
    }

    class Adapter1 extends BaseQuickAdapter<String, BaseViewHolder> {
        public Adapter1(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv_name, item);
        }
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mShowType = 0;
                mAdapter.setNewData(getData());
                mSwipeRl.setRefreshing(false);
            }
        }, 1500);
    }
    /**
     * author   Bright
     * date     2018/10/28 20:54
     * desc
     * RecycleView的分割线
     */
    class ColorDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Paint mPaint;
        private int mDividerHeight;//分割线高度

        public ColorDividerItemDecoration() {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);//抗锯齿
            mPaint.setColor(Color.RED);
        }
        /**
         * @param outRect 外侧边框,类似于margin
         * @param view    item条目view,出去margin的中间部分
         * @param parent  RecycleView
         * @param state   RecycleView的当前状态
         */
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            //第一个item不要绘制分割线
            if (parent.getChildAdapterPosition(view) != 0) {
                outRect.top = 1;
                //分割线是1px
                mDividerHeight = 1;
            }
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = parent.getChildAt(i);
                int index = parent.getChildAdapterPosition(view);
                if (index == 0) {//第一个item不需要绘制
                    continue;
                }
                float dividerLeft = parent.getPaddingLeft();
                float dividerTop = view.getTop() - mDividerHeight;
                float dividerRight = parent.getWidth()-parent.getPaddingRight();
                float dividerBottom =view.getTop();

                c.drawRect(dividerLeft,dividerTop,dividerRight,dividerBottom,mPaint);
            }
        }
    }
}
