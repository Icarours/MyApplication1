package com.syl.myapplication1.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.syl.myapplication1.R;
import com.syl.myapplication1.domain.Forecast;
import com.syl.myapplication1.domain.Forecast2;
import com.syl.myapplication1.utils.StringTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 天气预报
 */
public class WeatherForecastActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CODE_SUCCESS = 1;
    private static final int CODE_ERROR = 2;
    private static final String TAG = WeatherForecastActivity.class.getSimpleName();
    @Bind(R.id.tv_tips)
    TextView mTvTips;
    //如果在成员变量出就new一个ArrayList,会出现一个小bug,每点一次查询,mForecastList中的数据就会增加一倍,所以mForecastList的实例化应该放在dealWithData()方法的前面,并在displayData()方法后面置空.
    private List<Forecast> mForecastList;
    private EditText mEtCity;
    private ListView mLvCity;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CODE_SUCCESS:
                    mForecastList = new ArrayList<>();
                    String jsonString = (String) msg.obj;
                    dealWithData(jsonString);//把解析出来的数据存储到ArrayList中去
                    displayData();//展示数据
                    mForecastList = null;
                    break;
                case CODE_ERROR:
                    break;
                default:
                    break;
            }
        }
    };

    //展示数据
    private void displayData() {
        CityAdapter cityAdapter = new CityAdapter(mForecastList);
        mLvCity.setAdapter(cityAdapter);
    }

    //把解析出来的数据存储到ArrayList中去
    private void dealWithData(String jsonString) {
        Gson gson = new Gson();
        Forecast2 forecast1 = gson.fromJson(jsonString, Forecast2.class);
        System.out.println("forecast1=="+forecast1);
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            if ("OK".equals(jsonObject.getString("desc"))) {
                JSONObject jsonData = jsonObject.getJSONObject("data");
                JSONArray forecastArray = jsonData.getJSONArray("forecast");
                for (int i = 0; i < forecastArray.length(); i++) {
                    JSONObject jsonForecast = (JSONObject) forecastArray.get(i);
                    String date = jsonForecast.getString("date");
                    String high = jsonForecast.getString("high");
                    //fengli解析出来的数据挺奇怪
                    //forecast==Forecast{date='6日星期五', high='高温 35℃', fengli='<![CDATA[<3级]]>', low='低温 24℃', fengxiang='东风', type='多云'}
                    String fengli = jsonForecast.getString("fengli");
                    String low = jsonForecast.getString("low");
                    String fengxiang = jsonForecast.getString("fengxiang");
                    String type = jsonForecast.getString("type");

                    Forecast forecast = new Forecast(date, high, fengli, low, fengxiang, type);
                    mForecastList.add(forecast);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        ButterKnife.bind(this);
        mTvTips.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvTips.setText("JSONObject,JSONArray解析json数据");
        mEtCity = findViewById(R.id.et_city);
        mLvCity = findViewById(R.id.lv_city);
        findViewById(R.id.btn_city).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        new Thread() {
            @Override
            public void run() {
                String city = mEtCity.getText().toString();
                try {
                    String encodeCity = URLEncoder.encode(city, "UTF-8");
                    //http://wthrcdn.etouch.cn/weather_mini?city=%E5%AE%9D%E9%B8%A1
                    String path = "http://wthrcdn.etouch.cn/weather_mini?city=" + encodeCity;
                    URL url = new URL(path);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(5 * 1000);
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        InputStream inputStream = connection.getInputStream();
                        String value = StringTool.decode(inputStream);
                        Message msg = Message.obtain();
                        msg.what = CODE_SUCCESS;
                        msg.obj = value;
                        mHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = Message.obtain();
                    msg.what = CODE_ERROR;
                    mHandler.sendMessage(msg);
                }
            }
        }.start();
    }

    private class CityAdapter extends BaseAdapter {
        private List<Forecast> mData;

        public CityAdapter(List<Forecast> forecastList) {
            mData = forecastList;
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
                convertView = View.inflate(WeatherForecastActivity.this, R.layout.list_item_city, null);
                holder.tvdate = convertView.findViewById(R.id.tv_date);
                holder.tvhigh = convertView.findViewById(R.id.tv_high);
                //fengli解析出来的数据挺奇怪,就不展示出来了,如果显示出来就会变成 fengli='<![CDATA[<3级]]>'
                //holder.tvfengli = convertView.findViewById(R.id.tv_fengli);
                holder.tvlow = convertView.findViewById(R.id.tv_low);
                holder.tvfengxiang = convertView.findViewById(R.id.tv_fengxiang);
                holder.tvtype = convertView.findViewById(R.id.tv_type);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Forecast forecast = mData.get(position);
            holder.tvdate.setText(forecast.getDate());
            holder.tvhigh.setText(forecast.getHigh());
            //holder.tvfengli.setText(forecast.getFengli());
            holder.tvlow.setText(forecast.getLow());
            holder.tvfengxiang.setText(forecast.getFengxiang());
            holder.tvtype.setText(forecast.getType());
            Log.d(TAG, "forecast==" + forecast);
            return convertView;
        }
    }

    class ViewHolder {
        TextView tvdate;
        TextView tvhigh;
        TextView tvfengli;
        TextView tvlow;
        TextView tvfengxiang;
        TextView tvtype;
    }
}
