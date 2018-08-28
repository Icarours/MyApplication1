package com.syl.googleplay3.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.syl.googleplay3.R;
import com.syl.googleplay3.base.BaseHolder;
import com.syl.googleplay3.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/8/1.
 *
 * @Describe
 * @Called
 */

public class LoadMoreHolder extends BaseHolder<Integer> {
    public static final int LOADMORE_NONE = 0;//没有加载更多
    public static final int LOADMORE_LOADING = 1;//正在加载更多
    public static final int LOADMORE_ERROR = 2;//加载更多失败
    @Bind(R.id.item_loadmore_container_loading)
    LinearLayout mItemLoadmoreContainerLoading;
    @Bind(R.id.item_loadmore_tv_retry)
    TextView mItemLoadmoreTvRetry;
    @Bind(R.id.item_loadmore_container_retry)
    LinearLayout mItemLoadmoreContainerRetry;

    @Override
    public View initHolderView() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_loadmore, null);
        //此处一定记着,写上这句代码,否则不能绑定视图资源文件,就会抛出找不到资源文件的异常
        ButterKnife.bind(this,holderView);
        return holderView;
    }

    @Override
    public void refreshHolderView(Integer curState) {
        mItemLoadmoreContainerLoading.setVisibility(View.GONE);
        mItemLoadmoreContainerRetry.setVisibility(View.GONE);
        switch (curState) {
            case LOADMORE_LOADING:
                mItemLoadmoreContainerLoading.setVisibility(View.VISIBLE);
                break;
            case LOADMORE_ERROR:
                mItemLoadmoreContainerRetry.setVisibility(View.VISIBLE);
                break;
            case LOADMORE_NONE:
                break;
            default:
                break;
        }
    }
}
