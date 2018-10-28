package com.syl.myapplication1.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.syl.myapplication1.R;
import com.syl.myapplication1.domain.TitleBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/10/27.
 *
 * @Describe 调用系统自带的相机, 打开相册.
 * @Called
 */
public class CameraFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = CameraFragment.class.getSimpleName();
    @Bind(R.id.btn_camera)
    Button mBtnCamera;
    @Bind(R.id.btn_gallery)
    Button mBtnGallery;
    @Bind(R.id.rv_pic)
    RecyclerView mRvPic;
    @Bind(R.id.tv_content)
    TextView mTvContent;
    @Bind(R.id.iv_iv1)
    ImageView mIvIv1;
    @Bind(R.id.iv_iv2)
    ImageView mIvIv2;
    private TitleBean mTitleBean;
    private List<Uri> mListUri;
    private PicAdapter mPicAdapter;

    @Override
    protected void init() {
        mTitleBean = (TitleBean) getActivity().getIntent().getSerializableExtra("bean");
    }

    @Override
    protected void initData() {
        mListUri = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_camera, null);
        ButterKnife.bind(this, rootView);
        initView2();
        initListener();
        return rootView;
    }

    private void initListener() {
        mBtnCamera.setOnClickListener(this);
        mBtnGallery.setOnClickListener(this);
    }

    public void initView2() {
        StringBuffer sb = new StringBuffer();
        sb.append(mTitleBean.getDescription() + "\n");
        sb.append("将拍照获得的照片或者从相册获得的照片存到数据集中,然后在RecycleView展示出来..\n" +
                "给RecycleView的条目添加分割线\n");
        mTvContent.setText(sb.toString());
        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvPic.setLayoutManager(layoutManager);
        mPicAdapter = new PicAdapter();
        mRvPic.setAdapter(mPicAdapter);
        mRvPic.addItemDecoration(new ColorDividerItemDecoration(mRvPic.getContext(),DividerItemDecoration.HORIZONTAL));//设置分割线
    }

    /**
     * author   Bright
     * date     2018/10/28 20:54
     * desc
     * RecycleView的分割线
     */
    class ColorDividerItemDecoration extends DividerItemDecoration {
        private Paint mPaint;
        private int mDividerHeight;//分割线高度

        /**
         * Creates a divider {@link RecyclerView.ItemDecoration} that can be used with a
         * {@link LinearLayoutManager}.
         *
         * @param context     Current context, it will be used to access resources.
         * @param orientation Divider orientation. Should be {@link #HORIZONTAL} or {@link #VERTICAL}.
         */
        public ColorDividerItemDecoration(Context context, int orientation) {
            super(context, orientation);
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
                mDividerHeight = 10;
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

    /**
     * author   Bright
     * date     2018/10/28 20:53
     * desc
     * RecycleView适配器,提供条目视图,绑定数据
     */
    class PicAdapter extends RecyclerView.Adapter<PicViewHolder> {
        @Override
        public PicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View itemView = View.inflate(getActivity(), R.layout.rv_item_pic, null);
            View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.rv_item_pic, parent, false);
            return new PicViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(PicViewHolder holder, int position) {
            Uri uri = mListUri.get(position);
            Glide.with(getActivity()).load(uri).into(holder.mImageView);
        }

        @Override
        public int getItemCount() {
            if (mListUri != null && mListUri.size() > 0) {
                return mListUri.size();
            }
            return 0;
        }
    }

    class PicViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;

        public PicViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_pic);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_camera:
                openCamera();
//                openCamera2();
                break;
            case R.id.btn_gallery:
                openGallery();
                break;
            default:
                break;
        }
    }

    private void openGallery() {
        xc = 0;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 111);
    }

    private void openCamera() {
        xc = 1;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, false);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private Bitmap bitmap;
    private int xc;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "系统相机拍照完成，resultCode=" + resultCode);
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            ContentResolver cr = getActivity().getContentResolver();
            try {
                if (xc == 0) {//相册
                    bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                } else {//拍照
                    bitmap = data.getParcelableExtra("data");
                }
                if (uri == null) {//从相册获得图片
                    uri = Uri.parse(MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, null, null));
                }
                mIvIv1.setImageBitmap(bitmap);
                Glide.with(getActivity()).load(uri).into(mIvIv2);
                mListUri.add(uri);
                mPicAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
