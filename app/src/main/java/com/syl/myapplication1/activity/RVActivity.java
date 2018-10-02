package com.syl.myapplication1.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.syl.myapplication1.R;
import com.syl.myapplication1.domain.Computer;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author   Bright
 * date     2018/9/9 11:00
 * desc
 * RecyclerView原生的简单demo
 */
public class RVActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.recycler)
    RecyclerView mRecycler;
    @Bind(R.id.tv_content)
    TextView mTvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);
        ButterKnife.bind(this);
        mTvContent.setText("RecyclerView原生的用法");
        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        ComputerAdapter adapter = new ComputerAdapter();

        mRecycler.setAdapter(adapter);
        mRecycler.setLayoutManager(manager);
    }


    class ComputerAdapter extends RecyclerView.Adapter<ComputerHolder> {
        List<Computer> mList = getDatas();

        @Override
        public ComputerHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View item = LayoutInflater.from(RVActivity.this).inflate(R.layout.list_item1, null);

            return new ComputerHolder(item);
        }

        @Override
        public void onBindViewHolder(ComputerHolder holder, int position) {
            Computer computer = mList.get(position);
            holder.tvName.setText(computer.getName());
            holder.tvDesc.setText(computer.getDesc());
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    class ComputerHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvDesc;

        public ComputerHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDesc = itemView.findViewById(R.id.tv_desc);
        }
    }

    public List<String> addDatas() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            datas.add("苹果超薄游戏本" + i);
        }
        return datas;
    }

    private List<Computer> getDatas() {
        List<Computer> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new Computer("苹果电脑" + i, "很好的电脑"));
        }
        return list;
    }

    @Override
    public void onClick(View v) {

    }
}
