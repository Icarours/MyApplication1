package com.syl.myapplication1.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.syl.myapplication1.R;
import com.syl.myapplication1.db.dao.UserAccountDao;
import com.syl.myapplication1.domain.UserAccount;

import java.util.List;

/**
 * 数据库事务
 */
public class SqlTransactionActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = SqlTransactionActivity.class.getSimpleName();
    private List<UserAccount> mAccountList;
    private ListView mLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_transaction);
        findViewById(R.id.btn_insert_data).setOnClickListener(this);
        findViewById(R.id.btn_modify).setOnClickListener(this);
        mLv = findViewById(R.id.lv);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_insert_data:
                insertData();//插入数据并展示
                refreshData();//数显显示ListView中的数据
                break;
            case R.id.btn_modify:
                modify();//修改数据
                refreshData();//刷新数据
                break;
            default:
                break;
        }
    }

    private void modify() {
        UserAccountDao userAccountDao = new UserAccountDao(SqlTransactionActivity.this);
        userAccountDao.modify();
        Log.d(TAG,"修改数据成功..");
    }

    private void refreshData() {
        queryAllData();//查询所有数据,展示到ListView
        AccountAdapter adapter = new AccountAdapter(mAccountList);
        mLv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void queryAllData() {
        UserAccountDao userAccountDao = new UserAccountDao(SqlTransactionActivity.this);
        mAccountList = userAccountDao.queryAll();
        Log.d(TAG,"查询所有成功..");
    }

    private void insertData() {
        UserAccountDao userAccountDao = new UserAccountDao(SqlTransactionActivity.this);
        for (int i = 0; i < 200; i++) {
            UserAccount userAccount = new UserAccount();
            userAccount.setName("Linda" + i);
            userAccount.setMoney(1000);
            userAccountDao.insertData(userAccount);
        }
        Log.d(TAG,"输入数据成功..");
    }

    class AccountAdapter extends BaseAdapter {
        private List<UserAccount> mData;

        public AccountAdapter(List<UserAccount> data) {
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
                convertView = View.inflate(SqlTransactionActivity.this, R.layout.list_item_user_account, null);
                holder.tvUserAccount = convertView.findViewById(R.id.tv_user_account);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            UserAccount userAccount = mData.get(position);
            holder.tvUserAccount.setText(userAccount.getName() + "--" + userAccount.getMoney());
            return convertView;
        }
    }

    class ViewHolder {
        TextView tvUserAccount;
    }
}
