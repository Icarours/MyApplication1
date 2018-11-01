package com.syl.myapplication1.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.syl.myapplication1.R;
import com.syl.myapplication1.domain.TitleBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/10/30.
 *
 * @Describe SearchView
 * @Called
 */
public class SearchViewFragment extends BaseFragment {
    @Bind(R.id.search_view)
    SearchView mSearchView;
    @Bind(R.id.rv_search_view)
    RecyclerView mRvSearchView;
    @Bind(R.id.tv_content)
    TextView mTvContent;
    private List<String> mList;
    private List<String> mSearchList = new ArrayList<>();
    private SearchAdapter mAdapter;
    private TitleBean mTitleBean;

    @Override
    protected void init() {
        mTitleBean = (TitleBean) getActivity().getIntent().getSerializableExtra("bean");
    }

    @Override
    protected void initData() {
        getData();
    }

    private void getData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mList.add("item---" + i);
            mList.add("search---" + i);
            mList.add("view---" + i);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_seach_view, container, false);
        ButterKnife.bind(this, rootView);
        StringBuffer sb = new StringBuffer();
        sb.append(mTitleBean.getDescription() + "\n");
        sb.append("搜索框暂时没有实现搜索功能");
        mTvContent.setText(sb.toString());
        //初始化RecyclerView
        initRecyclerView();
        //初始化搜索框
        initSearchView();
        initListener();//初始化监听器

        return rootView;
    }

    private void initListener() {
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.d("mSearchView", "onClose==");
                SearchAdapter searchAdapter = new SearchAdapter(mList);
                mRvSearchView.setAdapter(searchAdapter);
                searchAdapter.notifyDataSetChanged();
                return true;
            }
        });
        mSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d("mSearchView", "onFocusChange--v==" + v + "--hasFocus==" + hasFocus);
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("mSearchView", "onQueryTextSubmit==" + query);
                if (mSearchList != null && mSearchList.size() > 0) {
                    mSearchList.clear();
                }
                for (int i = 0; i < mList.size(); i++) {
                    String s = mList.get(i);
                    if (aContainB(s, query)) {
                        mSearchList.add(s);
                    }
                }
                SearchAdapter searchAdapter = new SearchAdapter(mSearchList);
                mRvSearchView.setAdapter(searchAdapter);
                searchAdapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("mSearchView", "onQueryTextChange==" + newText);
                return true;
            }
        });
        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mSearchView", "onClick--v==" + v);
            }
        });
        mSearchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                Log.d("mSearchView", "onSuggestionSelect--position==" + position);
                return true;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                Log.d("mSearchView", "onSuggestionClick--position==" + position);
                return true;
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvSearchView.setLayoutManager(layoutManager);

        mAdapter = new SearchAdapter(mList);
        mRvSearchView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), LinearLayout.VERTICAL);
        mRvSearchView.addItemDecoration(dividerItemDecoration);
    }

    /**
     * 初始化搜索框
     */
    private void initSearchView() {
        //搜索图标是否显示在搜索框内
        mSearchView.setIconifiedByDefault(true);
        //设置搜索框展开时是否显示提交按钮，可不显示
        mSearchView.setSubmitButtonEnabled(true);
        //让键盘的回车键设置成搜索
        mSearchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        //搜索框是否展开，false表示展开
        mSearchView.setIconified(false);
        //获取焦点
        mSearchView.setFocusable(true);
        mSearchView.requestFocusFromTouch();
        //设置提示词
        mSearchView.setQueryHint("请输入关键字");
        //设置输入框文字颜色
        EditText editText = (EditText) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        editText.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        editText.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * author   Bright
     * date     2018/10/30 22:04
     * desc
     */
    class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {
        private List<String> data;

        public SearchAdapter(List<String> data) {
            this.data = data;
        }

        @Override
        public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.rv_item_tv, parent, false);
            return new SearchViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(SearchViewHolder holder, int position) {
            String s = data.get(position);
            holder.tvItem.setText(s);
        }

        @Override
        public int getItemCount() {
            if (data != null && data.size() > 0) {
                return data.size();
            } else {
                return 0;
            }
        }
    }

    /**
     * author   Bright
     * date     2018/10/30 22:07
     * desc
     * 设置RecyclerView的条目点击事件
     */
    class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvItem;

        public SearchViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvItem = itemView.findViewById(R.id.tv_item);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Toast.makeText(getContext(), adapterPosition + "---was clicked...", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean aContainB(String a, String b) {
        char[] charsA = a.toCharArray();
        char[] charsB = b.toCharArray();
        if (a.length() >= b.length()) {
            for (int i = 0; i < charsB.length; i++) {
                if (charsA[i] == charsB[i]) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
}
