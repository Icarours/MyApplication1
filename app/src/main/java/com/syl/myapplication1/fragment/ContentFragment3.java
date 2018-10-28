package com.syl.myapplication1.fragment;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.syl.myapplication1.R;
import com.syl.myapplication1.activity.Content3Activity;
import com.syl.myapplication1.domain.TitleBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/7/18.
 *
 * @Describe 主菜单(底部菜单)对应的第三个按钮
 * @Called
 */

public class ContentFragment3 extends BaseFragment {
    @Bind(R.id.rv_content3)
    RecyclerView mRvContent3;
    private List<TitleBean> mList;

    @Override
    protected void init() {
        Intent intent = getActivity().getIntent();//拥有本Fragment的Activity,可以从Activity哪儿获得数据
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        mList.add(new TitleBean(1,"相机,相册", "调用系统相机,相册"));
        for (int i = 100; i < 120; i++) {
            mList.add(new TitleBean(i,"title--" + i, "description--" + i));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_content3, null);
        ButterKnife.bind(this, rootView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvContent3.setLayoutManager(layoutManager);

        TitleAdapter titleAdapter = new TitleAdapter();
        mRvContent3.setAdapter(titleAdapter);
        mRvContent3.addItemDecoration(new ColorDividerItemDecoration());

        return rootView;
    }

    /**
     * author   Bright
     * date     2018/10/27 17:56
     * desc
     * RecycleView的Adapter
     * 在onCreateViewHolder中给RecycleView条目添加点击事件
     */
    class TitleAdapter extends RecyclerView.Adapter<TitleHolder> {

        @Override
        public TitleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //inflate()方法内的第二个参数为null,
            View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.rv_item_title, null);

            TitleHolder titleHolder = new TitleHolder(itemView);
            return titleHolder;
        }

        @Override
        public void onBindViewHolder(TitleHolder holder, int position) {
            TitleBean titleBean = mList.get(position);
            holder.title.setText(titleBean.getTitle());
            holder.description.setText(titleBean.getDescription());
        }

        @Override
        public int getItemCount() {
            if (mList != null && mList.size() > 0) {
                return mList.size();
            }
            return 0;
        }
    }

    /**
     * author   Bright
     * date     2018/10/27 17:56
     * desc
     * ViewHolder,封装条目中的控件
     * 直接在ViewHolder中设置点击事件和长按点击事件
     */
    class TitleHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView title, description;

        public TitleHolder(View itemView) {
            super(itemView);
            //直接在ViewHolder中设置点击事件和长按点击事件
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            title = itemView.findViewById(R.id.tv_title);
            description = itemView.findViewById(R.id.tv_desc);
            description.setMovementMethod(ScrollingMovementMethod.getInstance());
        }

        @Override
        public void onClick(View v) {
//            Toast.makeText(getActivity(), "position =="+getAdapterPosition()+"--was clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(),Content3Activity.class);
            TitleBean bean = mList.get(getAdapterPosition());
            intent.putExtra("bean",bean);
            startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            Toast.makeText(getActivity(), "position =="+getAdapterPosition()+"--was clicked  onLongClick", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
