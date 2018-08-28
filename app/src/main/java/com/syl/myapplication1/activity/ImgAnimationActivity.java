package com.syl.myapplication1.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.syl.myapplication1.R;
import com.syl.myapplication1.view.CustomView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 简单的img动画
 */
public class ImgAnimationActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.btn_translate)
    Button mBtnTranslate;
    @Bind(R.id.btn_rotate)
    Button mBtnRotate;
    @Bind(R.id.btn_scale_x)
    Button mBtnScale;
    @Bind(R.id.btn_mirror)
    Button mBtnMirror;
    @Bind(R.id.tv_tips)
    TextView mTvTips;
    @Bind(R.id.img_src)
    ImageView mImgSrc;
    @Bind(R.id.iv_target)
    ImageView mIvTarget;
    @Bind(R.id.btn_mirror2)
    Button mBtnMirror2;
    @Bind(R.id.cv)
    CustomView mCv;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_animation);
        ButterKnife.bind(this);
        mBtnTranslate.setOnClickListener(this);
        mBtnRotate.setOnClickListener(this);
        mBtnScale.setOnClickListener(this);
        mBtnMirror.setOnClickListener(this);
        mBtnMirror2.setOnClickListener(this);
        mTvTips.setText("简单的img动画");
        mTvTips.setMovementMethod(ScrollingMovementMethod.getInstance());
        mImgSrc.setImageResource(R.mipmap.ic_launcher);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        mCv.setBitmap(mBitmap);
        mIvTarget.setVisibility(View.GONE);
        mImgSrc.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_translate:
                mCv.setBitmap(mBitmap);
//                imgTranslate();
                mCv.translate(50, 50);
                break;
            case R.id.btn_rotate:
                mCv.setBitmap(mBitmap);
                imgRotate();
                mCv.rotate(30);
                break;
            case R.id.btn_scale_x:
                mCv.setBitmap(mBitmap);
                imgScale();
                mCv.setScaleX(0.5f);
                break;
            case R.id.btn_mirror:
                mCv.setBitmap(mBitmap);
                imgMirror();
                mCv.mirror();
                break;
            case R.id.btn_mirror2:
                mCv.setBitmap(mBitmap);
                imgMirror2();
                mCv.shadow();
                break;
            default:
                break;
        }
    }

    private void imgMirror2() {

    }

    private void imgMirror() {

    }

    private void imgScale() {

    }

    private void imgRotate() {

    }

    private void imgTranslate() {
        Bitmap srcBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Bitmap targetBitmap = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), srcBitmap.getConfig());
        Canvas canvas = new Canvas(targetBitmap);
        Paint paint = new Paint();
        Matrix matrix = new Matrix();
        matrix.setTranslate(srcBitmap.getWidth(), 0);
        canvas.drawBitmap(srcBitmap, matrix, paint);
        mIvTarget.setImageBitmap(targetBitmap);
    }
}
