package com.syl.myapplication1.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.syl.myapplication1.R;
import com.syl.myapplication1.domain.NewsItem;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 新闻v1版本 多级xml文件解析(重要)
 */
public class NewsActivity extends AppCompatActivity {

    private static final String TAG = NewsActivity.class.getSimpleName();
    private static final int CODE_SUCCESS = 1;
    private static final int CODE_ERROR = 2;
    @Bind(R.id.tv_tips)
    TextView mTvTips;
    private ListView mLvNews;
    private FrameLayout mFlLoading;
    private List<NewsItem> mItemList = new ArrayList<>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CODE_SUCCESS:
                    NewsAdapter newsAdapter = new NewsAdapter(mItemList);
                    mLvNews.setAdapter(newsAdapter);
                    mProgressDialog.dismiss();//进度条消失
                    mFlLoading.setVisibility(View.GONE);//FrameLayout隐藏Gone
                    break;
                case CODE_ERROR:
                    break;
                default:
                    break;
            }
        }
    };
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        mTvTips.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvTips.setText("多级xml文件解析(重要)");
        mFlLoading = findViewById(R.id.fl_loading);
        mLvNews = findViewById(R.id.lv_news);
        mFlLoading.setVisibility(View.VISIBLE);//加载数据的时候FrameLayout和ProgressDialog显示.
        // 数据加载结束后,FrameLayout和ProgressDialog消失
        mProgressDialog = new ProgressDialog(NewsActivity.this);
        mProgressDialog.setTitle("loading .....");
        mProgressDialog.show();
        displayView();
    }

    private void displayView() {
        new Thread() {
            @Override
            public void run() {
                mItemList = loadData();
                if (mItemList.size() > 0) {//说明获得了数据
                    Message msg = Message.obtain();
                    msg.what = CODE_SUCCESS;
                    //只需要传递状态码,成功与否.要处理的数据已经存储在成员变量List<NewsItem> mItemList里面了
                    mHandler.sendMessage(msg);
                } else {//没有获得数据
                    Message msg = Message.obtain();
                    msg.what = CODE_ERROR;
                    mHandler.sendMessage(msg);
                }
            }
        }.start();
    }

    //网络请求,解析多级xml文件(重要)
    private List<NewsItem> loadData() {
        //http://192.168.31.89:80/img/news.xml
        String path = "http://192.168.31.89:80/img/news.xml";
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5 * 1000);
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                InputStream inputStream = connection.getInputStream();
                //这两行待在这儿,data可以正常输出.
//                String decode = StringTool.decode(inputStream);
//                Log.d(TAG, "data==" + decode);
                XmlPullParser pullParser = Xml.newPullParser();
                pullParser.setInput(inputStream, "UTF-8");
// 这两行代码放在这儿的时候,data="".推测应该是XmlPullParser对inputStream进行了处理,处理之后直接关流了
//                String decode = StringTool.decode(inputStream);
//                Log.d(TAG, "data==" + decode);
                int eventType = pullParser.getEventType();
                NewsItem newsItem = null;
                while (eventType != XmlPullParser.END_DOCUMENT) {//只要不是结束标签
                    if (eventType == XmlPullParser.START_TAG) {//如果是开始标签
                        if ("item".equals(pullParser.getName())) {//如果标签名是"item"
                            newsItem = new NewsItem();//new一个实体对象
                        } else if ("title".equals(pullParser.getName())) {//如果标签名是"title"
                            String title = pullParser.nextText();//下一行文本
                            newsItem.setTitle(title);
                        } else if ("description".equals(pullParser.getName())) {
                            String description = pullParser.nextText();//下一行文本
                            newsItem.setDescription(description);
                        } else if ("image".equals(pullParser.getName())) {
                            String image = pullParser.nextText();//下一行文本
                            newsItem.setImage(image);
                        } else if ("type".equals(pullParser.getName())) {
                            int type = Integer.parseInt(pullParser.nextText());//下一行文本
                            newsItem.setType(type);
                        } else if ("comment".equals(pullParser.getName())) {
                            int comment = Integer.parseInt(pullParser.nextText());//下一行文本
                            newsItem.setComment(comment);
                        }
                    } else if (eventType == XmlPullParser.END_TAG) {
                        if ("item".equals(pullParser.getName())) {
                            mItemList.add(newsItem);//将封装收的数据存入List集合中
                        }
                    }
                    eventType = pullParser.next();//下一行,下一个事件,一行就是一个事件.pullParser解析器每次读取一行,一行一行的解析
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "list==" + mItemList);
        return mItemList;
    }

    private class NewsAdapter extends BaseAdapter {
        List<NewsItem> mData;

        public NewsAdapter(List<NewsItem> data) {
            mData = data;
        }

        @Override
        public int getCount() {
            if (mData != null) {
                return mData.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mData != null) {
                return mData.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            if (mData != null) {
                return position;
            }
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(NewsActivity.this, R.layout.list_item_news, null);
                holder.iv = convertView.findViewById(R.id.iv_img);
                holder.tvTiltle = convertView.findViewById(R.id.tv_title);
                holder.tvDescription = convertView.findViewById(R.id.tv_description);
                holder.tvType = convertView.findViewById(R.id.tv_type_news);
                holder.tvComment = convertView.findViewById(R.id.tv_comment);
                holder.iv = convertView.findViewById(R.id.iv_img);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            NewsItem newsItem = mData.get(position);
            holder.tvTiltle.setText(newsItem.getTitle());
            holder.tvDescription.setText(newsItem.getDescription());
            holder.tvType.setText(newsItem.getType() + "");//int类型的值必须加上""
            holder.tvComment.setText(newsItem.getComment() + "");
            String imagePath = newsItem.getImage();
            Glide.with(NewsActivity.this).load(imagePath).into(holder.iv);
            return convertView;
        }
    }

    class ViewHolder {
        /**
         * private String tiltle;
         * private String description;
         * private String image;
         * private int type;//评论,视频,直播
         * private int comment;
         */
        ImageView iv;
        TextView tvTiltle;
        TextView tvDescription;
        TextView tvType;
        TextView tvComment;
    }
}
