package com.syl.myapplication1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.syl.myapplication1.R;

import org.devio.takephoto.model.TImage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ResultActivity extends AppCompatActivity {
    ArrayList<TImage> images;
    @Bind(R.id.rv_photo)
    RecyclerView mRvPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        images = (ArrayList<TImage>) intent.getSerializableExtra("images");

        GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        mRvPhoto.setLayoutManager(manager);
        PhotoAdapter adapter = new PhotoAdapter(R.layout.list_item_image_single, images);
        mRvPhoto.setAdapter(adapter);
    }

    class PhotoAdapter extends BaseQuickAdapter<TImage, BaseViewHolder> {
        public PhotoAdapter(int layoutResId, @Nullable List<TImage> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, TImage item) {
            ImageView iv = helper.getView(R.id.iv);
            Glide.with(ResultActivity.this).load(new File(item.getCompressPath())).into(iv);
        }
    }

}
