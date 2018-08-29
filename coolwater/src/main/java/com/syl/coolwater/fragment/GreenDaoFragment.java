package com.syl.coolwater.fragment;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.syl.coolwater.R;
import com.syl.coolwater.bean.User;
import com.syl.coolwater.greendao.DaoMaster;
import com.syl.coolwater.greendao.DaoSession;
import com.syl.coolwater.greendao.UserDao;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/8/29.
 *
 * @Describe
 * @Called
 */

public class GreenDaoFragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.btn_insert)
    Button mBtnInsert;
    @Bind(R.id.btn_del)
    Button mBtnDel;
    @Bind(R.id.btn_update)
    Button mBtnUpdate;
    @Bind(R.id.btn_query)
    Button mBtnQuery;
    @Bind(R.id.btn_query_all)
    Button mBtnQueryAll;
    @Bind(R.id.tv_content)
    TextView mTvContent;
    private UserDao mUserDao;

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public View initView() {
        View rootView = View.inflate(getActivity(), R.layout.fragment_green_dao, null);
        ButterKnife.bind(this, rootView);
        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvContent.setText(getString(R.string.green_dao_tv_content) );
        mBtnDel.setOnClickListener(this);
        mBtnInsert.setOnClickListener(this);
        mBtnUpdate.setOnClickListener(this);
        mBtnQuery.setOnClickListener(this);
        mBtnQueryAll.setOnClickListener(this);
        return rootView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
//        UserDao dao = DaoManager.getInstance().getSession().getUserDao();//封装后,获取userDao
        DaoMaster.DevOpenHelper userDb = new DaoMaster.DevOpenHelper(getActivity(), "user_db", null);
        DaoMaster daoMaster = new DaoMaster(userDb.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();
        mUserDao = daoSession.getUserDao();
        switch (v.getId()) {
            case R.id.btn_insert:
                insertUser();
                break;
            case R.id.btn_del:
                userDel();
                break;
            case R.id.btn_update:
                userUpdate();
                break;
            case R.id.btn_query:
                queryOne();
                break;
            case R.id.btn_query_all:
                queryAll();
                break;
            default:
                break;
        }
    }

    private void queryAll() {
        new Thread() {
            @Override
            public void run() {
                List<User> list = mUserDao.queryBuilder().build().list();
                for (int i = 0; i < list.size(); i++) {
                    System.out.println(list.get(i).toString());
                }
            }
        }.start();
    }

    private void queryOne() {
        new Thread() {
            @Override
            public void run() {
                final User findUser = mUserDao.queryBuilder().where(UserDao.Properties.Name.eq("张三20")).build().unique();
                showToast("数据查找成功..");
                System.out.println(findUser.toString());
            }
        }.start();
    }

    private void userUpdate() {
        new Thread() {
            @Override
            public void run() {
                User findUser = mUserDao.queryBuilder().where(UserDao.Properties.Name.eq("张三10")).build().unique();
                if (findUser == null) {
                    showToast("找不到该用户..");
                } else {
                    findUser.setName("lisi10");
                    mUserDao.update(findUser);
                    showToast("数据修改成功..");
                }
            }
        }.start();
    }

    private void userDel() {
        new Thread() {
            @Override
            public void run() {
                List<User> userList = mUserDao.queryBuilder().where(UserDao.Properties.Name.eq("张三5")).build().list();
                for (int i = 0; i < userList.size(); i++) {
                    mUserDao.deleteByKey(userList.get(i).getId());
                }
                showToast("数据删除成功..");
            }
        }.start();
    }


    private void insertUser() {
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 50; i++) {
                    mUserDao.insert(new User(null, "张三" + i));//实体类的id不要指定值,让它自增
                }
                showToast("数据插入成功..");
                System.out.println("数据插入成功..");
            }
        }.start();
    }

    private void showToast(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
