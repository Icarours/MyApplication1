package com.syl.myapplication1.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.syl.myapplication1.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 涂鸦
 */
public class TuyaActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener, SeekBar.OnSeekBarChangeListener {

    @Bind(R.id.btn_red)
    Button mBtnRed;
    @Bind(R.id.btn_green)
    Button mBtnGreen;
    @Bind(R.id.btn_blue)
    Button mBtnBlue;
    @Bind(R.id.btn_save)
    Button mBtnSave;
    @Bind(R.id.seekBar)
    SeekBar mSeekBar;
    @Bind(R.id.iv)
    ImageView mIv;
    @Bind(R.id.tv_tips)
    TextView mTvTips;
    @Bind(R.id.btn_clear)
    Button mBtnClear;
    private Paint mPaint;
    private float mDownX;
    private float mDownY;
    private Bitmap mBitmap;
    private Canvas mCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuya);
        ButterKnife.bind(this);
        mTvTips.setText("涂鸦...");
        mTvTips.setMovementMethod(ScrollingMovementMethod.getInstance());
        mBtnBlue.setOnClickListener(this);
        mBtnRed.setOnClickListener(this);
        mBtnGreen.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);
        mBtnClear.setOnClickListener(this);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
        mPaint.setStrokeWidth(5f);
        mPaint.setColor(0x33ff0000);//设置默认颜色
        mIv.setOnTouchListener(this);
        mSeekBar.setOnSeekBarChangeListener(this);

        //保证控件mIvImg绘制完成,注册全局监听
        ViewTreeObserver viewTreeObserver = mIv.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                mBitmap = Bitmap.createBitmap(mIv.getWidth(), mIv.getHeight(), Bitmap.Config.ARGB_8888);
                mCanvas = new Canvas(mBitmap);
                mIv.getViewTreeObserver().removeOnGlobalLayoutListener(this);//注销自己
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_red:
                mPaint.setColor(Color.RED);
                break;
            case R.id.btn_green:
                mPaint.setColor(Color.GREEN);
                break;
            case R.id.btn_blue:
                mPaint.setColor(Color.BLUE);
                break;
            case R.id.btn_save:
                saveImg();
                break;
            case R.id.btn_clear:
                mCanvas.drawColor(0, PorterDuff.Mode.CLEAR);//清空画布
                break;
            default:
                break;
        }
    }

    /**
     * 保存图片
     */
    private void saveImg() {
        File file = new File("/mnt/sdcard/" + System.currentTimeMillis() + ".jpg");
        try {
            OutputStream outputStream = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            Intent intent = new Intent();
            //搜索一遍文件,保证新保存的文件出现在文件夹中
            intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            sendBroadcast(intent);
            Toast.makeText(this, "图片保存成功..", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();//获得当前的x轴点
                mDownY = event.getY();//获得当前的y轴点
                // Log.d(this.getClass().getSimpleName(), "ACTION_DOWN---mDownX==" + mDownX + "---mDownY===" + mDownY);
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float moveY = event.getY();
                mCanvas.drawLine(mDownX, mDownY, moveX, moveY, mPaint);
                mIv.setImageBitmap(mBitmap);

                //再次赋值起始位置
                mDownX = moveX;
                mDownY = moveY;
                // Log.d(this.getClass().getSimpleName(), "ACTION_MOVE---mDownX==" + mDownX + "---mDownY===" + mDownY +
                //        "moveX==" + moveX + "---moveY===" + moveY);
                break;
            case MotionEvent.ACTION_UP:
                //Log.d(this.getClass().getSimpleName(), "ACTION_UP");
                break;
            default:
                break;
        }
        //返回值是true代表自己消费touch事件,不再向下或者向上传递
        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        //设置画笔宽度,
        mPaint.setStrokeWidth(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
